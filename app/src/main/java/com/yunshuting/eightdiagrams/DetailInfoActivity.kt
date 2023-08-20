package com.yunshuting.eightdiagrams

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabItem
import com.yunshuting.eightdiagrams.databinding.ActivityDetailInfoBinding
import com.yunshuting.eightdiagrams.mv.MyDBHelper
import com.yunshuting.eightdiagrams.mv.MyFragmentPagerAdapter
import com.yunshuting.eightdiagrams.ui.YaoFragment


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

//            initTab(name)
            initTab2(name)
            binding.viewPager.requestLayout()
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

    private fun initTab2(name:String) {
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        val Fragment1 = YaoFragment()
        val Fragment2 = YaoFragment()
        val Fragment3 = YaoFragment()
        val Fragment4 = YaoFragment()
        val Fragment5 = YaoFragment()
        val Fragment6 = YaoFragment()
        val Fragment7 = YaoFragment()
        Fragment1.initData(yao1,curGuaNum,1)
        Fragment2.initData(yao2,curGuaNum,2)
        Fragment3.initData(yao3,curGuaNum,3)
        Fragment4.initData(yao4,curGuaNum,4)
        Fragment5.initData(yao5,curGuaNum,5)
        Fragment6.initData(yao6,curGuaNum,6)
        Fragment7.initData(yao7,curGuaNum,7)
        //Fragment.newInstance("111")
        var fragments = listOf(Fragment1, Fragment2, Fragment3,Fragment4,Fragment5,Fragment6)
        var titles = listOf(getYaoTitle(name,1), getYaoTitle(name,2), getYaoTitle(name,3)
            , getYaoTitle(name,4), getYaoTitle(name,5), getYaoTitle(name,6))
        if(TextUtils.equals("乾卦",name) || TextUtils.equals("坤卦",name)) {
            fragments = listOf(Fragment1, Fragment2, Fragment3,Fragment4,Fragment5,Fragment6,Fragment7)
            titles = listOf(getYaoTitle(name,1), getYaoTitle(name,2), getYaoTitle(name,3)
                , getYaoTitle(name,4), getYaoTitle(name,5), getYaoTitle(name,6),getYaoTitle(name,7))
        }

        val adapter = MyFragmentPagerAdapter(supportFragmentManager, fragments, titles)
        viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(viewPager)
    }

    private fun getYaoTitle(name:String,index:Int):String {
        if (index == 7) {
            if (name == "乾卦") {
                return "用九"
            }
            if (name == "坤卦") {
                return "用六"
            }
        }
        return  "第${index}爻"
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

    }

}