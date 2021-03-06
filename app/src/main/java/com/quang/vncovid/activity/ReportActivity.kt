package com.quang.vncovid.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.quang.vncovid.R
import com.quang.vncovid.adapter.ReportAdapter
import com.quang.vncovid.data.model.ReportModel
import com.quang.vncovid.databinding.ActivityReportBinding

class ReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportBinding

    private val listReport = mutableListOf<ReportModel>()
    private lateinit var reportAdapter: ReportAdapter
    private val phoneNumber = FirebaseAuth.getInstance().currentUser?.phoneNumber ?: "+84989767127"
    private val firestore = FirebaseFirestore.getInstance()
    private lateinit var tvNoItem: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView = binding.recyclerView
        tvNoItem = binding.tvNoItem

        val launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    if (data?.getBooleanExtra("create_or_update", false) == true) {
                        getData()
                    }
                }
            }


        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(ItemTouchHelper.END, ItemTouchHelper.START)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (viewHolder is ReportAdapter.ViewHolder) {
                    removeItem(viewHolder.adapterPosition)
                }
            }
        })

        reportAdapter = ReportAdapter(launcher)
        recyclerView.adapter = reportAdapter
        itemTouchHelper.attachToRecyclerView(recyclerView)
        getData()

        binding.fab.setOnClickListener {
            launcher.launch(Intent(this, DetailReportActivity::class.java))
        }
    }

    private fun getData() {
        listReport.clear()
        val loading = binding.componentLoading.loading
        firestore.collection("account").document(phoneNumber).collection("report")
            .orderBy("date", Query.Direction.DESCENDING).get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val reportModel = document.toObject(ReportModel::class.java)
                    reportModel.id = document.id
                    listReport.add(reportModel)
                }

                reportAdapter.submitList(listReport)
                reportAdapter.notifyItemRangeChanged(0, listReport.size)

                loading.visibility = View.GONE
                if (listReport.isEmpty()) {
                    tvNoItem.visibility = View.VISIBLE
                } else {
                    tvNoItem.visibility = View.GONE
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "C?? l???i x???y ra.", Toast.LENGTH_SHORT).show()
                loading.visibility = View.GONE
            }
    }

    private fun removeItem(position: Int) {
        val item = reportAdapter.currentList[position]
        firestore.collection("account").document(phoneNumber).collection("report").document(item.id)
            .delete()
            .addOnSuccessListener {
                listReport.removeAt(position)
                reportAdapter.notifyItemRemoved(position)
                Toast.makeText(this, "X??a th??nh c??ng", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "C?? l???i x???y ra.", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_report, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            FirebaseAuth.getInstance().signOut()
            finish()
        } else {
            finish()
        }
        return true
    }
}
