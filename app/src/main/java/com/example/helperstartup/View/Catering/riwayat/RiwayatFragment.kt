package com.example.helperstartup.View.Catering.riwayat

import android.os.Build
import android.os.Bundle
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

@RequiresApi(Build.VERSION_CODES.N)
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
        showData(listOf())
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
            adapter = adapter
            setHasFixedSize(true)
        }
    }

    private fun showData (listHistory : List<HistoryModel>?) {
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