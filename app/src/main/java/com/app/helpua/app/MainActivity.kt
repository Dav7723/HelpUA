package com.app.helpua.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.app.helpua.data.datasources.firebase.AuthDataSource
import com.app.helpua.data.datasources.firebase.DataBaseDataSource
import com.app.helpua.databinding.ActivityMainBinding
import com.app.helpua.domain.models.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}