package com.example.virtualshop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.virtualshop.databinding.ActivityMainBinding
import com.example.virtualshop.databinding.CategoriaBinding
import com.example.virtualshop.databinding.MeninferiorBinding

class MainActivity : AppCompatActivity() {

    private  lateinit var Cbinding : CategoriaBinding
    private  lateinit var binding : ActivityMainBinding
    private lateinit var  Mbinding : MeninferiorBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val categoriaAdapter = CategoriaAdapter()
        Cbinding = CategoriaBinding.inflate(layoutInflater)
        Cbinding.rvCategoria.adapter = categoriaAdapter


        Mbinding = MeninferiorBinding.inflate(layoutInflater)
        setContentView(Mbinding.root)

        Mbinding.BottomNavigationView.setOnItemReselectedListener {
            when(it.itemId){
                R.id.buscar -> replaceFragment(fragCategoria())
                R.id.usuarios -> replaceFragment(Usuario())
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.framelayout, fragment)
        fragmentTransaction.commit()
    }


}