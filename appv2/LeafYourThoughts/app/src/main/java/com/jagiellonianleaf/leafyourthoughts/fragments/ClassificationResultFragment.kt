package com.jagiellonianleaf.leafyourthoughts.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.jagiellonianleaf.leafyourthoughts.MLClassifierViewModel
import com.jagiellonianleaf.leafyourthoughts.R

class ClassificationResultFragment : Fragment() {
    private val viewModel: MLClassifierViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val classificationResultObserver = Observer<String> { newResult ->
            view?.findViewById<TextView>(R.id.classificationResultTextView)?.text = newResult
        }   // TODO: present results in a proper way
        viewModel.classificationResult.observe(this, classificationResultObserver)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater
            .inflate(R.layout.fragment_classification_result, container, false)
    }
}