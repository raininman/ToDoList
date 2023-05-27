package com.example.todolist.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.R
import com.example.todolist.domain.Item
import com.google.android.material.textfield.TextInputLayout

class ItemFragment(
):Fragment() {

    private lateinit var tilName: TextInputLayout
    private lateinit var tilDescription: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etDescription: EditText
    private lateinit var buttonSave: Button


    private var screenMode:String = MODE_UNKNOWN
    private var itemId:Int = Item.UNDEFINED_ID


    private lateinit var viewModel: ItemViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ItemViewModel::class.java]
        initViews(view)
        addTextChangeListeners()
        launchRightMode()
        observeViewModel()
    }


    private fun observeViewModel() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                "Error while getting name"
            } else {
                null
            }
            tilName.error = message
        }
        viewModel.errorInputDescription.observe(viewLifecycleOwner) {
            val message = if (it) {
                "Error while getting description"
            } else {
                null
            }
            tilDescription.error = message
        }
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun addTextChangeListeners() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        etDescription.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputDescription()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun launchEditMode() {
        viewModel.getItem(itemId)
        viewModel.item.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etDescription.setText(it.description)
        }
        buttonSave.setOnClickListener {
            viewModel.editItem(etName.text?.toString(), etDescription.text?.toString())
        }
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            viewModel.addItem(etName.text?.toString(), etDescription.text?.toString())
        }
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilDescription = view.findViewById(R.id.til_description)
        etName = view.findViewById(R.id.et_name)
        etDescription = view.findViewById(R.id.et_description)
        buttonSave = view.findViewById(R.id.save_button)
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(ITEM_ID)) {
                throw RuntimeException("Param item id is absent")
            }
            itemId = args.getInt(ITEM_ID, Item.UNDEFINED_ID)
        }
    }

    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val ITEM_ID = "extra_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem():ItemFragment{
            return ItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(itemId: Int):ItemFragment{
            return ItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(ITEM_ID, itemId )
                }
            }
        }
    }
}