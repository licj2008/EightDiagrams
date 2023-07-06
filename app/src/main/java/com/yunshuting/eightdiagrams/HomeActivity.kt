package com.yunshuting.eightdiagrams

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.yunshuting.eightdiagrams.databinding.ActivityHomeBinding
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    var mIsTryUse = true
    var isYaoGuaing = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.run {
            hide()
        }

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView


        val navController = findNavController(R.id.nav_host_fragment_activity_home)

//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)



        navView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    if(isYaoGuaing) {
                        Toast.makeText(this, "摇卦中", Toast.LENGTH_SHORT).show()
                    } else {
                        navController.navigate(R.id.navigation_home)
                    }
                    true
                }
                R.id.navigation_dashboard -> {
                    // 处理"搜索"选项的点击事件
                    if(isYaoGuaing) {
                        //Toast.makeText(this, "摇卦中", Toast.LENGTH_SHORT).show()
                    } else {
                        navController.navigate(R.id.navigation_dashboard)
                    }

//                    val fragment = DashboardFragment()
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.nav_host_fragment_activity_home, fragment, fragment.javaClass.canonicalName)
//                        .commit()
                    true
                }
                R.id.navigation_notifications -> {
                    // 处理"个人资料"选项的点击事件
                    if(isYaoGuaing) {
                        Toast.makeText(this, "摇卦中", Toast.LENGTH_SHORT).show()
                    } else {
                        navController.navigate(R.id.navigation_notifications)
                    }

//                    val fragment = NotificationsFragment()
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.nav_host_fragment_activity_home, fragment, fragment.javaClass.simpleName)
//                        .commit()
                    true
                }
                else -> false
            }
        }
        isTryVersion()
    }

    //判断是否在试用期
    fun isInTryUse():Boolean {
        return mIsTryUse;
    }

    fun isTryVersion() {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = sdf.format(Date())
        val targetDate = "2023-12-01"
        val compareResult = currentDate.compareTo(targetDate)
        println("当前日期为：$currentDate")
        println("目标日期为：$targetDate")
        if (compareResult > 0) {
            mIsTryUse = false
            println("当前日期晚于目标日期")
        } else if (compareResult < 0) {
            mIsTryUse = true
            println("当前日期早于目标日期")
        } else {
            mIsTryUse = true
            println("当前日期等于目标日期")
        }

    }

    fun setYaoing(b:Boolean) {
        isYaoGuaing = b
    }
}