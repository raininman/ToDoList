package com.example.todolist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.R
import com.example.todolist.domain.Item
import com.google.android.material.textfield.TextInputLayout

class ItemActivity : AppCompatActivity() {
//    private lateinit var tilName: TextInputLayout
//    private lateinit var tilDescription: TextInputLayout
//    private lateinit var etName: EditText
//    private lateinit var etDescription: EditText
//    private lateinit var buttonSave: Button

    private var screenMode = MODE_UNKNOWN
    private var itemId = Item.UNDEFINED_ID


//    private lateinit var viewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        parseIntent()
//        viewModel = ViewModelProvider(this)[ItemViewModel::class.java]
//        initViews()
//        addTextChangeListeners()
        launchRightMode()
//        observeViewModel()
    }

//    private fun observeViewModel() {
//        viewModel.errorInputName.observe(this) {
//            val message = if (it) {
//                "Error while getting name"
//            } else {
//                null
//            }
//            tilName.error = message
//        }
//        viewModel.errorInputDescription.observe(this) {
//            val message = if (it) {
//                "Error while getting description"
//            } else {
//                null
//            }
//            tilDescription.error = message
//        }
//        viewModel.shouldCloseScreen.observe(this) {
//            finish()
//        }
//    }
//
    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> ItemFragment.newInstanceEditItem(itemId)
            MODE_ADD -> ItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
    supportFragmentManager.beginTransaction()
        .add(R.id.item_container, fragment)
        .commit()
    }
//
//    private fun addTextChangeListeners() {
//        etName.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.resetErrorInputName()
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//            }
//
//        })
//        etDescription.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.resetErrorInputDescription()
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//            }
//
//        })
//    }
//
//    private fun launchEditMode() {
//        viewModel.getItem(itemId)
//        viewModel.item.observe(this) {
//            etName.setText(it.name)
//            etDescription.setText(it.description)
//        }
//        buttonSave.setOnClickListener {
//            viewModel.editItem(etName.text?.toString(), etDescription.text?.toString())
//        }
//    }
//
//    private fun launchAddMode() {
//        buttonSave.setOnClickListener {
//            viewModel.addItem(etName.text?.toString(), etDescription.text?.toString())
//        }
//    }
//
//    private fun initViews() {
//        tilName = findViewById(R.id.til_name)
//        tilDescription = findViewById(R.id.til_description)
//        etName = findViewById(R.id.et_name)
//        etDescription = findViewById(R.id.et_description)
//        buttonSave = findViewById(R.id.save_button)
//    }

    private fun parseIntent() {
        if (!intent.hasExtra(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(ITEM_ID)) {
                throw RuntimeException("Param item id is absent")
            }
            itemId = intent.getIntExtra(ITEM_ID, Item.UNDEFINED_ID)
        }
    }

    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val ITEM_ID = "extra_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""


        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ItemActivity::class.java)
            intent.putExtra(SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, itemId: Int): Intent {
            val intent = Intent(context, ItemActivity::class.java)
            intent.putExtra(SCREEN_MODE, MODE_EDIT)
            intent.putExtra(ITEM_ID, itemId)
            return intent
        }
    }
}