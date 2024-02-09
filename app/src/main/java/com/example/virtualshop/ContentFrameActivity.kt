package com.example.virtualshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.virtualshop.databinding.ActivityContentFrameBinding
import com.example.virtualshop.fragments.Home2Fragment
import com.example.virtualshop.fragments.OrderFragment
import com.example.virtualshop.fragments.ProfileFragment
import com.example.virtualshop.fragments.SearchFragment
import com.example.virtualshop.fragments.Shopping2Fragment
import com.example.virtualshop.localstorage.GlobalAppication
import com.google.android.material.navigation.NavigationBarView

class ContentFrameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContentFrameBinding
    private val mOnNavMenu = NavigationBarView.OnItemSelectedListener {
        when(it.itemId){
            R.id.itemHome -> {
                supportFragmentManager.commit {
                    replace<Home2Fragment>(R.id.flFrameContent)
                    setReorderingAllowed(true)
                    addToBackStack("Home")
                }
                return@OnItemSelectedListener true
            }

            R.id.itemShoppingCar -> {
                supportFragmentManager.commit {
                    replace<Shopping2Fragment>(R.id.flFrameContent)
                    setReorderingAllowed(true)
                    addToBackStack("Shopping")
                }
                return@OnItemSelectedListener true
            }

            R.id.itemSearch -> {
                supportFragmentManager.commit {
                    replace<SearchFragment>(R.id.flFrameContent)
                    setReorderingAllowed(true)
                    addToBackStack("Search")
                }
                return@OnItemSelectedListener true
            }

            R.id.orderProfile -> {
                supportFragmentManager.commit {
                    replace<OrderFragment>(R.id.flFrameContent)
                    setReorderingAllowed(true)
                    addToBackStack("Order")
                }
                return@OnItemSelectedListener true
            }

            R.id.itemProfile -> {
                supportFragmentManager.commit {
                    replace<ProfileFragment>(R.id.flFrameContent)
                    setReorderingAllowed(true)
                    addToBackStack("Profile")
                }
                return@OnItemSelectedListener true
            }

        }

        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContentFrameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navMenu.setOnItemSelectedListener(mOnNavMenu)

        supportFragmentManager.commit {
            replace<Home2Fragment>(R.id.flFrameContent)
            setReorderingAllowed(true)
            addToBackStack("Home")
        }

        binding.svSearcherProducts.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(!query.isNullOrBlank()){
                    binding.svSearcherProducts.clearFocus()
                    val bundle = Bundle()
                    bundle.putString("NAME_PRODUCT", query)

                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<SearchFragment>(R.id.flFrameContent, args = bundle)
                        addToBackStack("Search")
                    }

                }else{
                    Toast.makeText(this@ContentFrameActivity, "Escriba el nombre del prodcuto a buscar", Toast.LENGTH_SHORT).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

    }


    override fun onStart() {
        super.onStart()

        if(GlobalAppication.prefs.getJWT().isEmpty()){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}