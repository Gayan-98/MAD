package com.example.tutor.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tutor.adapter.ClassAdapter
import com.example.tutor.model.ClassModel
import com.example.tutor.R
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class FetchingActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var classRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var searchView: SearchView
    private lateinit var classList: ArrayList<ClassModel>
    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        classRecyclerView = findViewById(R.id.rvClass)
        classRecyclerView.layoutManager = LinearLayoutManager(this)
        classRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)
        searchView = findViewById(R.id.searchView)

        classList = arrayListOf<ClassModel>()

        // set up the SearchView
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = "Search for classes"

        // get data from Firebase
        getclassData()

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (!TextUtils.isEmpty(newText)) {
            search(newText!!)
        } else {
            getclassData()
        }
        return true
    }

    private fun search(query: String) {
        val searchResultList = arrayListOf<ClassModel>()
        for (classModel in classList) {
            if (classModel.className?.toLowerCase(Locale.ROOT)?.contains(query.toLowerCase()) == true) {
                searchResultList.add(classModel)
            }
        }
        val mAdapter = ClassAdapter(searchResultList)
        classRecyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener(object : ClassAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@FetchingActivity, ClassDetailsActivity::class.java)
                //put extras
                intent.putExtra("classId", searchResultList[position].classId)
                intent.putExtra("className", searchResultList[position].className)
                intent.putExtra("tutorName", searchResultList[position].tutorName)
                intent.putExtra("location", searchResultList[position].location)
                intent.putExtra("phoneNumber", searchResultList[position].contNumber)
                intent.putExtra("clsEmail", searchResultList[position].clsEmail)
                startActivity(intent)
            }
        })
    }

    private fun getclassData() {
        classRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE
        dbRef = FirebaseDatabase.getInstance().getReference("Tutor")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                classList.clear()
                if (snapshot.exists()) {
                    for (classSnap in snapshot.children) {
                        val classData = classSnap.getValue(ClassModel::class.java)
                        classList.add(classData!!)
                    }
                    val mAdapter = ClassAdapter(classList)
                    classRecyclerView.adapter = mAdapter
                    mAdapter.setOnItemClickListener(object : ClassAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent =
                                Intent(this@FetchingActivity, ClassDetailsActivity::class.java)
                            //put extras
                            intent.putExtra("classId", classList[position].classId)
                            intent.putExtra("className", classList[position].className)
                            intent.putExtra("tutorName", classList[position].tutorName)
                            intent.putExtra("location", classList[position].location)
                            intent.putExtra("phoneNumber", classList[position].contNumber)
                            intent.putExtra("clsEmail", classList[position].clsEmail)
                            startActivity(intent)
                        }
                    })
                } else {
                    tvLoadingData.text = "No classes found."
                }
                tvLoadingData.visibility = View.GONE
                classRecyclerView.visibility = View.VISIBLE
            }

            override fun onCancelled(error: DatabaseError) {
                tvLoadingData.text = "Error: ${error.message}"
                tvLoadingData.visibility = View.VISIBLE
                classRecyclerView.visibility = View.GONE
            }

        })
    }
}
