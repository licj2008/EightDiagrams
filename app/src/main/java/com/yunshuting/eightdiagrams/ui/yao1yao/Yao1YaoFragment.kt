package com.yunshuting.eightdiagrams.ui.yao1yao

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.yunshuting.eightdiagrams.DetailInfoActivity
import com.yunshuting.eightdiagrams.HomeActivity
import com.yunshuting.eightdiagrams.R
import com.yunshuting.eightdiagrams.bean.DiagramBean
import com.yunshuting.eightdiagrams.databinding.FragmentYao1yaoBinding
import com.yunshuting.eightdiagrams.mv.DateUtil
import com.yunshuting.eightdiagrams.mv.MyDBHelper
import com.yunshuting.eightdiagrams.mv.MyUtils
import com.yunshuting.eightdiagrams.mv.ShakeDetector
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.ref.WeakReference
import java.util.*


val START_SHAKE:Int = 1001
val END_SHAKE:Int = 1002
var isshake = false
class Yao1YaoFragment : Fragment(), SensorEventListener {
    var index = 0;
    var result = intArrayOf(-1, -1, -1, -1, -1, -1);
    var upDiagram: DiagramBean? = null;
    var downDiagram: DiagramBean? = null;
    lateinit var sensorManager: SensorManager
    lateinit var shakeDetector: ShakeDetector

    lateinit var mHandler:MyHandler


    private var _binding: FragmentYao1yaoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentYao1yaoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sensorManager = activity?.getSystemService(SENSOR_SERVICE) as SensorManager
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        binding.flipButton.setOnClickListener {
            shake();
        }
        binding.resetButton.setOnClickListener {
            reset();
        }
        binding.downloadButton.setOnClickListener {
            downloadPic();
        }
        MyUtils.initDagramsData()
        val mAccelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (mAccelerometerSensor != null) {
            sensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_UI);
        }
        mHandler = MyHandler(this)
        if (!(activity as HomeActivity).isYaoGuaing) {
            reset()
        }

    }

    override fun onPause() {
        super.onPause()
        // 取消传感器监听器的注册
        sensorManager.unregisterListener(this)
    }
    private fun reset() {
        index = 0;
        result = intArrayOf(-1, -1, -1, -1, -1, -1);
        upDiagram = null;
        downDiagram = null;
        binding.resultText.text ="静心诚意，摇6次得一卦"
        binding.tvPoem.text = ""
        binding.tv6yaoList.text = "[]"
        binding.ivZygua.visibility = View.GONE
        (activity as HomeActivity).setYaoing(false)
        binding.llCoins.visibility = View.VISIBLE
        binding.clDownload.visibility = View.GONE
        binding.downloadButton.visibility = View.GONE
    }

    fun downloadPic() {
        //val view = YourView(context) // 替换为你的视图
        val view = binding.clDownload // 替换为你的视图
        val bitmap = generateBitmapFromView(view)
        //saveBitmapToGallery(requireContext(), bitmap, "image.png")
        saveImageToGallery(requireContext(),bitmap)
    }
    /**
     * 摇卦中，每一次摇得阴阳情况进行显示
     */
    private fun showRealTimeYao() {
        var s ="";
        for(item in result) {
            var curYaoName = " "
            if(item == -1){
                curYaoName = " "
            } else if(item == 0){
                curYaoName = "阴"
            } else if(item == 1){
                curYaoName = "阳"
            }
            s += curYaoName
            s += ","
        }
        s = s.substring(0,s.length-1)
        binding.tv6yaoList.text = "[" + s + "]"
    }

    public fun shake(){
        if (index > 5) {
            Toast.makeText(activity,"卦已成，请先点“重新开始”！",Toast.LENGTH_LONG).show()
            (activity as HomeActivity).setYaoing(false)

            return
        }

        if (!(activity as HomeActivity).isInTryUse()) {
            Toast.makeText(activity,"出了点问题，请联系开发者",Toast.LENGTH_LONG).show()
            return
        }

        val coin1 = flipCoin()
        val coin2 = flipCoin()
        val coin3 = flipCoin()
        Log.i("licj","setOnClickListener");
        binding.coin1Image.setImageResource(getCoinImage(coin1))
        binding.coin2Image.setImageResource(getCoinImage(coin2))
        binding.coin3Image.setImageResource(getCoinImage(coin3))

        doVibrator(requireContext())

        (activity as HomeActivity).setYaoing(true)
        if (index < 6) {
            index++
            Log.d("licj","cur index,val ="+index+","+getResultText(coin1, coin2, coin3).toString());
            val curValue = getResultText(coin1, coin2, coin3)
            val curYao :String = if (curValue==1) "阳" else "阴";
            result.set(index-1,curValue)
            binding.resultText.text ="第 $index 次 共6次("+curYao+")"
        }
        Log.d("licj","cur index 7777 ="+index);
        if (index > 2){
            val curbin = result.get(2).toString()+result.get(1).toString()+result.get(0).toString();
            downDiagram = MyUtils.getDagramByNo(MyUtils.getDiagramNoByYao(curbin));
        }
        if (index > 5) {
            val curbin = result.get(5).toString()+result.get(4).toString()+result.get(3).toString();
            upDiagram = MyUtils.getDagramByNo(MyUtils.getDiagramNoByYao(curbin));
            binding.clDownload.visibility = View.VISIBLE
        }
        showRealTimeYao()
        //Log.d("licj",result.toList().toString());
        if (upDiagram != null && downDiagram != null){
            getDataByUpdown(upDiagram?.DiagramName+"上"+downDiagram?.DiagramName+"下");
        }
    }

    private fun doVibrator(ctx: Context){
        if (ctx != null) {
            val vibrator = ctx.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(400, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vibrator.vibrate(400)
            }
        }
    }

    private fun flipCoin(): Boolean {
        return Random().nextBoolean()
    }

    private fun getCoinImage(isHeads: Boolean): Int {
        return if (isHeads) R.drawable.ic_coin_heads else R.drawable.ic_coin_tails
    }

    private fun getResultText(coin1: Boolean, coin2: Boolean, coin3: Boolean): Int {
        val numHeads = listOf(coin1, coin2, coin3).count { it }
        return when (numHeads) {
            0 -> 1 //"All tails"
            1 -> 0 //"One head, two tails"
            2 -> 1 //"Two heads, one tail"
            3 -> 0 //"All heads"
            else -> -1
        }
    }

    private fun getDataByid(id:Int) {
        Log.d("licj","getDataByid 000 ");
        val db = MyDBHelper.getDatabase(requireContext())
        Log.d("licj","getDataByid 555 ");
        val cursor = db.query(
            MyDBHelper.TABLE_NAME, null, "num=?", arrayOf(id.toString()), null, null, null
        )
        Log.d("licj","getDataByid 666 ");
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("num"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            Log.d("licj","getDataByid 777 id,name ="+id+","+name);
            // Handle query result here.
        }

        cursor.close()
    }

    private fun getDataByUpdown(updown:String) {
        Log.d("licj","getDataByid 000 ");
        val db = MyDBHelper.getDatabase(requireContext())
        Log.d("licj","getDataByid 555 ");
        val cursor = db.query(
            MyDBHelper.TABLE_NAME, null, "updown=?", arrayOf(updown), null, null, null
        )
        Log.d("licj","getDataByid 666 ");
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("num"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            var guaxiang = cursor.getString(cursor.getColumnIndexOrThrow("guaxiang"))
            guaxiang = guaxiang.replace("，","\n").replace("。","\n")
            Log.d("licj","getDataByid 777 id,name ="+id+","+name);
//            binding.resultText.text = name

            val imgid= getResources().getIdentifier("gua"+id.toString(),"drawable",requireContext().packageName)
            binding.ivZygua.setImageResource(imgid)
            binding.ivZygua.visibility = View.VISIBLE
            binding.ivZygua.setOnClickListener {
                val intent = Intent( binding.ivZygua?.context, DetailInfoActivity::class.java)
                intent.putExtra("guaNum", id)
                binding.ivZygua?.context?.startActivity(intent)
            }
            // Handle query result here.
            binding.resultText.text = binding.resultText.text.toString() +"\n摇中：" +name
            showPoem(guaxiang)
            showDownloadCard()
            binding.llCoins.visibility = View.GONE
        }

        cursor.close()
    }

    private fun showPoem(text:String) {
        val charArray = text.toCharArray()
        val handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                binding.tvPoem.text = binding.tvPoem.text.toString() + charArray[msg.arg1]
            }
        }
        val runnable = object : Runnable {
            var index = 0
            override fun run() {
                if (index < charArray.size) {
                    val message = Message.obtain()
                    message.arg1 = index
                    handler.sendMessage(message)
                    index++
                    handler.postDelayed(this, 200)
                }
            }
        }
        runnable.run()
    }

    fun showDownloadCard() {
        //val layoutParams = binding.ivBg.imageView.layoutParams
        val imageWidth = getScreenWidth(requireContext());
        val imageHeight = imageWidth * 10 / 16

        Glide.with(binding.ivBg.context)
            .load(R.drawable.card_bg)
            .fitCenter()
            .override(imageWidth,imageHeight)
            .into(binding.ivBg)

        binding.tvDlDate.text = DateUtil.getCurTime()
        binding.tvBottom.text ="《易经卜卦》"
        binding.downloadButton.visibility = View.VISIBLE
    }

    fun getScreenWidth(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    override fun onSensorChanged(event: SensorEvent?) {
//        shakeDetector?.onSensorChanged(event)
        //Toast.makeText(requireContext(), "SHAKE~~~!!!!", Toast.LENGTH_SHORT).show()
        val type = event!!.sensor.type
        if (type == Sensor.TYPE_ACCELEROMETER) {
            //获取三个方向值
            val values = event.values
            val x = values[0]
            val y = values[1]
            val z = values[2]
            if ((Math.abs(x) > 17 || Math.abs(y) > 17 || Math
                    .abs(z) > 17) && !isshake
            ) {
                isshake = true
                // TODO: 2016/10/19 实现摇动逻辑, 摇动后进行震动
                val thread: Thread = object : Thread() {
                    override fun run() {
                        super.run()
                        try {
                            //开始震动 发出提示音 展示动画效果
                            mHandler.obtainMessage(START_SHAKE).sendToTarget()
                            sleep(1000)
                            mHandler.obtainMessage(END_SHAKE).sendToTarget();
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                }
                thread.start()
            }
        }


    }

    // 生成视图的Bitmap
    fun generateBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    // 保存Bitmap到相册
//    fun saveBitmapToGallery(context: Context, bitmap: Bitmap, fileName: String) {
//        val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//        val imageFile = File(storageDir, fileName)
//
//        try {
//            val outputStream: OutputStream = FileOutputStream(imageFile)
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
//            outputStream.flush()
//            outputStream.close()
//
//            // 通知图库更新
//            context.sendBroadcast(android.content.Intent(android.content.Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
//                android.net.Uri.fromFile(imageFile)))
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

    fun saveImageToGallery(context: Context, bitmap: Bitmap): Boolean {
        // 检查是否有写入外部存储的权限
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // 如果没有权限，则申请权限
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
            return false
        }

        // 创建保存图片的目录
        val galleryDirectory = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "YourAppName"
        )

        // 如果目录不存在，则创建目录
        if (!galleryDirectory.exists()) {
            galleryDirectory.mkdirs()
        }

        // 生成图片文件名
        //val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val timeStamp: String = "abc"
        val fileName = "IMG_$timeStamp.jpg"

        // 保存图片到指定路径
        val file = File(galleryDirectory, fileName)
        try {
            val outputStream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        // 将图片添加到相册媒体库
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DATA, file.absolutePath)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
        }

        context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )

        return true
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    class MyHandler(activity: Yao1YaoFragment) : Handler() {
        private val mReference: WeakReference<Yao1YaoFragment>
        private lateinit var mActivity: Yao1YaoFragment

        init {
            mReference = WeakReference<Yao1YaoFragment>(activity)
            if (mReference != null) {
                mActivity = mReference.get()!!
            }
        }

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                START_SHAKE -> {
                    //todo
                    Log.d("licj", "onSensorChanged: 摇动")
                    mActivity.shake()
                }
                END_SHAKE -> {
                    isshake = false
                }
            }
        }
    }
}