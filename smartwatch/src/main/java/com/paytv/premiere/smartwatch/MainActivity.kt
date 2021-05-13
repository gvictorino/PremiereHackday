package com.paytv.premiere.smartwatch

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.paytv.premiere.smartwatch.databinding.ActivityMainBinding

class MainActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.bt1.setOnClickListener {
            changeFragment(ListMatchesFragment())
        }

        binding.bt2.setOnClickListener {
            changeFragment(Fragment())
        }
    }

    private fun changeFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainFrameLayout, fragment)
        transaction.commit()
    }
}
