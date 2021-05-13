package com.paytv.premiere.smartwatch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paytv.premiere.smartwatch.databinding.FragmentListMatchesBinding
import com.paytv.premiere.smartwatch.databinding.ItemTextBinding

class ListMatchesFragment : Fragment() {

    private lateinit var binding: FragmentListMatchesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListMatchesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.matchesWearableRecycler.layoutManager =
            LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.matchesWearableRecycler.adapter =
            MatchesAdapter(listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1))

    }
}

class MatchesAdapter(
    private val list: List<Any>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MatchViewHolder(
            ItemTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MatchViewHolder).bind()
    }

    override fun getItemCount() = list.size
}

class MatchViewHolder(
    private val binding: ItemTextBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {
    }
}
