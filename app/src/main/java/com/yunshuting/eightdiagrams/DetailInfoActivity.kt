package com.yunshuting.eightdiagrams

import android.os.Bundle
import android.os.Environment
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.yunshuting.eightdiagrams.databinding.ActivityDetailInfoBinding
import com.yunshuting.eightdiagrams.mv.MyDBHelper
import java.io.File


class DetailInfoActivity : AppCompatActivity() {
    private var curGuaNum: Int = -1
    private lateinit var binding: ActivityDetailInfoBinding
    private var yao1 =""
    private var yao2 =""
    private var yao3 =""
    private var yao4 =""
    private var yao5 =""
    private var yao6 =""
    private var yao7 =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        curGuaNum = intent.getIntExtra("guaNum", -1);
    }

    override fun onResume() {
        super.onResume()
        if (curGuaNum < 0 || curGuaNum > 64)
        {
            //todo show empty page
            return
        }
        showInfo(curGuaNum)
    }

    private fun showInfo(curGuaNum :Int) {

        val db = MyDBHelper.getDatabase(this)
        Log.d("licj","getDataByid 555 ");
        val cursor = db.query(
            MyDBHelper.TABLE_NAME, null, "num=?", arrayOf(curGuaNum.toString()), null, null, null
        )
        Log.d("licj","getDataByid 666 ");
        while (cursor.moveToNext()) {
            val num = cursor.getInt(cursor.getColumnIndexOrThrow("num"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val name2 = cursor.getString(cursor.getColumnIndexOrThrow("name2"))
            Log.d("licj","getDataByid 777 id,name ="+num+","+name);
            val huNum = cursor.getInt(cursor.getColumnIndexOrThrow("hugua"))
            val huName = cursor.getString(cursor.getColumnIndexOrThrow("huguaname"))
            val cuoNum = cursor.getInt(cursor.getColumnIndexOrThrow("cuogua"))
            val cuoName = cursor.getString(cursor.getColumnIndexOrThrow("cuoguaname"))
            val zongNum = cursor.getInt(cursor.getColumnIndexOrThrow("zonggua"))
            val zongName = cursor.getString(cursor.getColumnIndexOrThrow("zongguaname"))
            val yuanwen = cursor.getString(cursor.getColumnIndexOrThrow("yuanwen"))
            val guaxiang = cursor.getString(cursor.getColumnIndexOrThrow("guaxiang"))
            val desc = cursor.getString(cursor.getColumnIndexOrThrow("desc1"))
            val qian = cursor.getString(cursor.getColumnIndexOrThrow("qian"))
            val shiye = cursor.getString(cursor.getColumnIndexOrThrow("shiyi"))
            val jingshang = cursor.getString(cursor.getColumnIndexOrThrow("jingshang"))
            val qiuming = cursor.getString(cursor.getColumnIndexOrThrow("qiuming"))
            val waichu = cursor.getString(cursor.getColumnIndexOrThrow("waichu"))
            val hunlian = cursor.getString(cursor.getColumnIndexOrThrow("hunlian"))
            val juece = cursor.getString(cursor.getColumnIndexOrThrow("juece"))
            val daxiang = cursor.getString(cursor.getColumnIndexOrThrow("daxiang"))
            val yunshi = cursor.getString(cursor.getColumnIndexOrThrow("yunshi"))
            val shiyun = cursor.getString(cursor.getColumnIndexOrThrow("shiyun"))
            val caiyun = cursor.getString(cursor.getColumnIndexOrThrow("caiyun"))
            val jiazhai = cursor.getString(cursor.getColumnIndexOrThrow("jiazhai"))
            val shenti = cursor.getString(cursor.getColumnIndexOrThrow("shenti"))
            val name3 = cursor.getString(cursor.getColumnIndexOrThrow("name3"))
            val base_yuanwen = cursor.getString(cursor.getColumnIndexOrThrow("base_yuanwen"))
            val base_jieshi = cursor.getString(cursor.getColumnIndexOrThrow("base_jieshi"))
            val base_zhexue = cursor.getString(cursor.getColumnIndexOrThrow("base_zhexue"))

            val guaDesc = "["+qian+"] "+desc + "\n\n" +
                    "卦象："+guaxiang+"\n\n" +
                    "【大象】："+daxiang+"\n\n" +
                    "【时运】："+shiyun+"\n\n" +
                    "【财运】："+caiyun+"\n\n" +
                    "【家宅】："+jiazhai+"\n\n" +
                    "【身体】："+shenti+"\n\n" +
                    "【运势】："+yunshi+"\n\n" +
                    "【事业】："+shiye+"\n\n" +
                    "【经商】："+jingshang+"\n\n" +
                    "【求名】："+qiuming+"\n\n" +
                    "【外出】："+waichu+"\n\n" +
                    "【婚恋】："+hunlian+"\n\n" +
                    "【决策】："+juece+"";
//            yao1 = cursor.getString(cursor.getColumnIndexOrThrow("jiuyao1"))
//            yao2 = cursor.getString(cursor.getColumnIndexOrThrow("jiuyao2"))
//            yao3 = cursor.getString(cursor.getColumnIndexOrThrow("jiuyao3"))
//            yao4 = cursor.getString(cursor.getColumnIndexOrThrow("jiuyao4"))
//            yao5 = cursor.getString(cursor.getColumnIndexOrThrow("jiuyao5"))
//            yao6 = cursor.getString(cursor.getColumnIndexOrThrow("jiuyao6"))
            yao1 = cursor.getString(cursor.getColumnIndexOrThrow("jiuyao_h1"))
            yao2 = cursor.getString(cursor.getColumnIndexOrThrow("jiuyao_h2"))
            yao3 = cursor.getString(cursor.getColumnIndexOrThrow("jiuyao_h3"))
            yao4 = cursor.getString(cursor.getColumnIndexOrThrow("jiuyao_h4"))
            yao5 = cursor.getString(cursor.getColumnIndexOrThrow("jiuyao_h5"))
            yao6 = cursor.getString(cursor.getColumnIndexOrThrow("jiuyao_h6"))

            if (name =="乾卦" || name =="坤卦") {
                yao7 = cursor.getString(cursor.getColumnIndexOrThrow("jiuyao7"))
            }

            // Handle query result here.
            binding.tvGuaTitle.text = "第"+num+"卦 "+name+" "+ name2;

            val benResId= getResources().getIdentifier("gua"+num.toString(),"drawable",this.packageName)
            //binding.ivBenGua.setImageResource(benResId)
            setImage(binding.ivBenGua, benResId)
            binding.tvBenTitle.text = num.toString()+" "+name;

            val huResId= getResources().getIdentifier("gua"+huNum.toString(),"drawable",this.packageName)
//            binding.ivHuGua.setImageResource(huResId)
            setImage(binding.ivHuGua, huResId)
            binding.tvHuTitle.text = huNum.toString()+" "+huName;
            binding.ivHuGua.setOnClickListener {
                showInfo(huNum)
            }

            val cuoResId= getResources().getIdentifier("gua"+cuoNum.toString(),"drawable",this.packageName)
            //binding.ivCuoGua.setImageResource(cuoResId)
            setImage(binding.ivCuoGua, cuoResId)
            binding.tvCuoTitle.text = cuoNum.toString()+" "+cuoName;
            binding.ivCuoGua.setOnClickListener {
                showInfo(cuoNum)
            }

            val zongResId= getResources().getIdentifier("gua"+zongNum.toString(),"drawable",this.packageName)
            //binding.ivZongGua.setImageResource(zongResId)
            setImage(binding.ivZongGua, zongResId)
            binding.tvZongTitle.text = zongNum.toString()+" "+zongName;
            binding.ivZongGua.setOnClickListener {
                showInfo(zongNum)
            }

            binding.tvGuaXiang.text = guaDesc
            //binding.tvDesc.text = yuanwen
            binding.tvName3.text = name3 + "\n\n【原文】:\n" + base_yuanwen +
                    "\n\n【解释】:\n" + base_jieshi +
                    "\n\n【哲学含义】:\n" + base_jieshi

            initTab(name)
            binding.tabLayout.selectTab(binding.tabLayout.getTabAt(1))
            binding.tabLayout.selectTab(binding.tabLayout.getTabAt(0))

        }

        cursor.close()
        db.close()
   }

    private fun ChangeImg(str: String, drawname: String):String{
        var result = str.replace("\r","")
            .replace("\n","")
            .replace("\t","")

        //<div style="text-align: center;">
        //<img alt="http://img.fututa.com/bk/yao/1_5.png" src="http://www.zhouyi.cc/uploads/allimg/141114/3-141114151050464.png" style="width: 178px; height: 112px;"/></div>

        val regex = "<div style=\"text-align: center;\">(.*?)</div>".toRegex()
        //val regex = "<img.*?src=\"(.*?)\".*?>".toRegex()
        //val regex = "<img.*?/>".toRegex()
        val matchResult = regex.find(result)
        val imageUrl = matchResult?.groupValues?.get(1)
        if (!TextUtils.isEmpty(imageUrl)){
            //result = str.replace(imageUrl!!,"@drawable/"+drawname)
            //val imgpath = "file:///android_res/drawable/" + drawname+".png"
            val imgpath = "<img src=‘myimage’>"
            result = result.replace(imageUrl!!,imgpath)
            Log.i("licj",imgpath)
        }
        return result
    }

    private fun setImage(imageView: ImageView, imgId: Int) {
        val layoutParams = imageView.layoutParams
        val imageWidth = layoutParams.width;
        val imageHeight = imageWidth * 202 /162
        Log.d("licj","setImage imgId ="+","+imgId.toString()+"，"+imageWidth+"，"+imageHeight);
        Glide.with(imageView.context)
            .load(imgId)
            .fitCenter()
            .override(imageWidth,imageHeight)
            .into(imageView)
    }

    private fun initTab(name:String) {
        binding.tabLayout.removeAllTabs()
        for (i in 1..6) {
            val tab = binding.tabLayout.newTab()
            val tabItem = TabItem(this)
            tab.text = "第${i}爻"
            binding.tabLayout.addTab(tab)
        }
        if (name == "乾卦") {
            val tab = binding.tabLayout.newTab()
            tab.text = "用九"
            binding.tabLayout.addTab(tab)
        }
        if (name == "坤卦") {
            val tab = binding.tabLayout.newTab()
            tab.text = "用六"
            binding.tabLayout.addTab(tab)
        }
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                var bianId = 0
                var drawname=""
                var html = ""
                when(tab?.position ?: 0){
                    0-> {
                        html = yao1
                        drawname = "gua"+curGuaNum.toString()+"_1"
                        bianId= getResources().getIdentifier(drawname,"drawable",packageName)
                    }
                    1-> {
                        html = yao2
                        drawname = "gua"+curGuaNum.toString()+"_2"
                        bianId= getResources().getIdentifier(drawname,"drawable",packageName)
                    }
                    2-> {
                        html = yao3
                        drawname = "gua"+curGuaNum.toString()+"_3"
                        bianId= getResources().getIdentifier(drawname,"drawable",packageName)
                    }
                   3-> {
                       html = yao4
                       drawname = "gua"+curGuaNum.toString()+"_4"
                        bianId= getResources().getIdentifier(drawname,"drawable",packageName)
                    }
                    4-> {
                        html = yao5
                        drawname = "gua"+curGuaNum.toString()+"_5"
                        bianId= getResources().getIdentifier(drawname,"drawable",packageName)
                    }
                    5-> {
                        html = yao6
                        drawname = "gua"+curGuaNum.toString()+"_6"
                        bianId= getResources().getIdentifier(drawname,"drawable",packageName)
                    }
                    6-> {
                        html = yao7
                    }
                }
                if (bianId > 0) {
                    html = ChangeImg(html, drawname)
                    val text: CharSequence =
                        Html.fromHtml(html, Html.ImageGetter { source -> //根据图片资源ID获取图片
                            Log.d("source", source)
                            //换资源
                            if (source == "‘myimage’") {
                                val draw = resources.getDrawable(bianId)
                                draw.setBounds(0, 0, draw.intrinsicWidth, draw.intrinsicHeight)
                                return@ImageGetter draw
                            }
                            null
                        }, null)
                    binding.tvYaoDesc.setText(text)
                } else {
                    binding.tvYaoDesc.setText(html)
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

    }

}