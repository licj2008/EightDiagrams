package com.yunshuting.eightdiagrams.ui

import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yunshuting.eightdiagrams.databinding.FragmentYaoBinding


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"


/**
 * A simple [Fragment] subclass.
 * Use the [YaoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class YaoFragment : Fragment() {
    private var param1: String? = null
    private var drawname: String?= null

    private lateinit var binding: FragmentYaoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentYaoBinding.inflate(layoutInflater)
        showYaoInfo()

        return binding.root
    }

    fun initData(info: String,curGuaNum:Int,yaoIndex: Int) {
        param1 = info
        drawname = "gua"+curGuaNum.toString()+"_"+yaoIndex.toString()

    }

    override fun onResume() {
        super.onResume()

    }

    private fun showYaoInfo() {
        val bianId = getResources().getIdentifier(drawname,"drawable",activity?.packageName)
        val html = ChangeImg(param1!!, drawname!!)
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
        binding.tvYaoDesc.requestLayout()
    }

    private fun ChangeImg(str: String, drawname: String):String{
        var result = str.replace("\r","")
            .replace("\n","")
            .replace("\t","")

        val regex = "<div style=\"text-align: center;\">(.*?)</div>".toRegex()
        val matchResult = regex.find(result)
        val imageUrl = matchResult?.groupValues?.get(1)
        if (!TextUtils.isEmpty(imageUrl)){
            val imgpath = "<img src=‘myimage’>"
            result = result.replace(imageUrl!!,imgpath)
            Log.i("licj",imgpath)
        }
        return result
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment YaoFragment.
         */
        @JvmStatic
        fun newInstance(param1: String) =
            YaoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}