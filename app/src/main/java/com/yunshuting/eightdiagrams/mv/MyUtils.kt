package com.yunshuting.eightdiagrams.mv

import com.yunshuting.eightdiagrams.bean.DiagramBean

object MyUtils {
    //lateinit var DiagramList;
    lateinit var diagramList:List<DiagramBean>;
    fun initDagramsData() :List<DiagramBean>{
        val curItem1 = DiagramBean(0,"坤","☷");
        val curItem2 = DiagramBean(4,"艮","☶");
        val curItem3 = DiagramBean(2,"坎","☵");
        val curItem4 = DiagramBean(6,"巽","☴");
        val curItem5 = DiagramBean(1,"震","☳");
        val curItem6 = DiagramBean(5,"离","☲");
        val curItem7 = DiagramBean(3,"兑","☱");
        val curItem8 = DiagramBean(7,"乾","☰");
        diagramList = listOf(curItem1,curItem2,curItem3,curItem4,curItem5,curItem6,curItem7,curItem8);
        return diagramList;
    }

    fun getDagramByNo(no:Int):DiagramBean{
        return diagramList.filter { it.DiagramNum == no }.get(0)
        //return diagramList.get(no);
    }

     fun getDiagramNoByYao( abin:String): Int {
        return when(abin){
            "000" -> 0
            "001" -> 1
            "010" -> 2
            "011" -> 3
            "100" -> 4
            "101" -> 5
            "110" -> 6
            "111" -> 7
            else -> -1
        }
    }
}