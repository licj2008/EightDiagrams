package com.yunshuting.eightdiagrams.ui.gua64

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.yunshuting.eightdiagrams.bean.DiagramBean
import com.yunshuting.eightdiagrams.databinding.FragmentGua64listBinding
import com.yunshuting.eightdiagrams.mv.MyAdapter
import com.yunshuting.eightdiagrams.mv.MyDBHelper
import kotlinx.android.synthetic.main.fragment_main.*

class Gua64ListFragment : Fragment() {
    var dataList =ArrayList<DiagramBean>()
    private var _binding: FragmentGua64listBinding? = null
    lateinit var madapter:MyAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGua64listBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        rcv_main.layoutManager = GridLayoutManager(requireContext(), 4)

        initGuaData()
        madapter = MyAdapter(dataList)
        //madapter = dataList?.let { MyAdapter(it) }
        rcv_main.adapter = madapter
    }

    private fun initGuaData(){
        dataList.clear()
        val db = MyDBHelper.getDatabase(requireContext())
        Log.d("licj","getDataByid 555 ");
        val cursor = db.query(
            MyDBHelper.TABLE_NAME, null, null, null, null, null, null
        )
        Log.d("licj","getDataByid 666 ");
        while (cursor.moveToNext()) {
            val item = DiagramBean(cursor.getInt(cursor.getColumnIndexOrThrow("num")),
                cursor.getString(cursor.getColumnIndexOrThrow("name")),"")
            dataList.add(item)
        }

        cursor.close()
        db.close()
    }
}