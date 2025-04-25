package com.jagiellonianleaf.leafyourthoughts.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jagiellonianleaf.leafyourthoughts.MLClassifierViewModel
import com.jagiellonianleaf.leafyourthoughts.R


class ClassificationResultFragment : Fragment() {
    private val viewModel: MLClassifierViewModel by activityViewModels()
    private val trees = mapOf(
        "maple" to Tree(R.string.maple_name, R.string.maple_description, R.string.maple_leaves, R.string.maple_flowers,
                        R.string.maple_fruits, R.string.maple_bark, R.string.maple_habitat, R.drawable.maple),
        "liriodendron" to Tree(R.string.liriodendron_name, R.string.liriodendron_description, R.string.liriodendron_leaves, R.string.liriodendron_flowers,
            R.string.liriodendron_fruits, R.string.liriodendron_bark, R.string.liriodendron_habitat, R.drawable.liriodendron),
        "red_hornbeam" to Tree(R.string.red_hornbeam_name, R.string.red_hornbeam_description, R.string.red_hornbeam_leaves, R.string.red_hornbeam_flowers,
            R.string.red_hornbeam_fruits, R.string.red_hornbeam_bark, R.string.red_hornbeam_habitat, R.drawable.red_hornbeam),
        "hornbeam" to Tree(R.string.hornbeam_name, R.string.hornbeam_description, R.string.hornbeam_leaves, R.string.hornbeam_flowers,
            R.string.hornbeam_fruits, R.string.hornbeam_bark, R.string.hornbeam_habitat, R.drawable.hornbeam),
        "royal_red_maple" to Tree(R.string.red_maple_name, R.string.red_maple_description, R.string.red_maple_leaves, R.string.red_maple_flowers,
            R.string.red_maple_fruits, R.string.red_maple_bark, R.string.red_maple_habitat, R.drawable.red_maple),
        "silver_linden" to Tree(R.string.silver_linden_name, R.string.silver_linden_description, R.string.silver_linden_leaves, R.string.silver_linden_flowers,
            R.string.silver_linden_fruits, R.string.silver_linden_bark, R.string.silver_linden_habitat, R.drawable.silver_linden),
        "small_leaved_linden" to Tree(R.string.small_linden_name, R.string.small_linden_description, R.string.small_linden_leaves, R.string.small_linden_flowers,
            R.string.small_linden_fruits, R.string.small_linden_bark, R.string.small_linden_habitat, R.drawable.small_linden),
        "birch" to Tree(R.string.birch_name, R.string.birch_description, R.string.birch_leaves, R.string.birch_flowers,
            R.string.birch_fruits, R.string.birch_bark, R.string.birch_habitat, R.drawable.birch))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val classificationResultObserver = Observer<String> { newResult ->
            val tree = trees[newResult]
            if (tree != null) {
                view?.findViewById<TextView>(R.id.classificationResultSpecies)?.text =
                    getString(tree.name)
                view?.findViewById<ImageView>(R.id.classificationResultImage)
                    ?.setImageResource(tree.image)
                view?.findViewById<TextView>(R.id.classificationResultDescription)?.text =
                    getString(tree.description)
                view?.findViewById<TextView>(R.id.classificationResultLeavesDetails)?.text =
                    getString(tree.leaves)
                view?.findViewById<TextView>(R.id.classificationResultFlowerDetails)?.text =
                    getString(tree.flowers)
                view?.findViewById<TextView>(R.id.classificationResultFruitsDetails)?.text =
                    getString(tree.fruits)
                view?.findViewById<TextView>(R.id.classificationResultBarkDetails)?.text =
                    getString(tree.bark)
                view?.findViewById<TextView>(R.id.classificationResultHabitatDetails)?.text =
                    getString(tree.habitat)
            }
        }
        viewModel.classificationResult.observe(this, classificationResultObserver)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_classification_result, container, false)
        view.findViewById<Button>(R.id.classificationResultGoBack).setOnClickListener {
                findNavController().popBackStack()
        }
        return view
    }
}

data class Tree(val name: Int, val description: Int, val leaves: Int, val flowers: Int,
                val fruits: Int, val bark: Int, val habitat:Int, val image: Int)