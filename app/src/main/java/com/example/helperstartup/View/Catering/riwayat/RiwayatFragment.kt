package com.example.helperstartup.View.Catering.riwayat

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helperstartup.Model.Data.HistoryModel
import com.example.helperstartup.View.Adapter.HistoryAdapter
import com.example.helperstartup.databinding.FragmentRiwayatBinding

@RequiresApi(Build.VERSION_CODES.O)
class RiwayatFragment : Fragment() {
    private var _binding: FragmentRiwayatBinding? = null
    private lateinit var historyAdapter : HistoryAdapter

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRiwayatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        showData(listOf(HistoryModel(null, "Paket A", "completed", "Lorem ipsum", null,"2022-12-31T09:55:00", 30000),
            HistoryModel(null, "Paket B", "cancelled", "Lorem ipsum", null,"2022-10-20T09:55:00", 40000)))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupView() {
        historyAdapter = HistoryAdapter()
        val recyclerView: RecyclerView = binding.rvHistory
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity().applicationContext)
            adapter = historyAdapter
            setHasFixedSize(true)
        }
    }

    private fun showData (listHistory : List<HistoryModel>?) {
        Log.d("riwayatList", listHistory.toString())
        if (listHistory != null && listHistory.isNotEmpty()) {
            historyAdapter.setListHistories(listHistory)
            binding.noItemText.visibility = View.GONE
        } else {
            binding.noItemText.visibility = View.VISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}