package com.app.deeplinktester.activity

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.deeplinktester.DeepLinkApplication
import com.app.deeplinktester.R
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.app.deeplinktester.adapter.DeepLinkListAdapter
import com.app.deeplinktester.databinding.ActivityMainBinding
import com.app.deeplinktester.room.model.DeepLinkData
import com.app.deeplinktester.viewmodel.DeepLinkViewModel
import com.app.deeplinktester.viewmodel.DeepLinkViewModelFactory
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private val deepLinkViewModel: DeepLinkViewModel by viewModels {
        DeepLinkViewModelFactory((application as DeepLinkApplication).repository)
    }

    private var adapter: DeepLinkListAdapter? = null
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setUpUi()
    }

    private fun setUpUi() {
        setUpActionButtonFunctionality()
        setUpRecyclerView()
        observeDataBase()
    }

    private fun observeDataBase() {
        deepLinkViewModel.allDeepLinks.observe(this) { deeplinks ->
            adapter?.submitList(deeplinks)
        }
    }

    private fun setUpRecyclerView() {
        adapter = DeepLinkListAdapter(object : DeepLinkListAdapter.ActionListener {
            override fun onPlayButtonClick(deeplink: String) {
                fireDeeplink(deeplink)
            }

            override fun onDeleteButtonClick(deeplink: String) {
                deepLinkViewModel.delete(deeplink)
            }

            override fun onCopyButtonClick(deeplink: String) {
                copyText(deeplink)
            }
        })
        binding?.recyclerview?.adapter = adapter
        binding?.recyclerview?.layoutManager = LinearLayoutManager(this)
    }

    private fun setUpActionButtonFunctionality() {
        // to enable and disable action button
        binding?.outlinedTextField?.editText?.doOnTextChanged { text, _, _, _ ->
            binding?.actionButton?.isEnabled = !text.isNullOrBlank()
        }

        binding?.actionButton?.setOnClickListener {
            fireDeeplink(binding?.outlinedTextField?.editText?.text.toString())
        }
    }

    private fun copyText(text: String) {
        val clipboard: ClipboardManager? = this.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("Deeplink Copied", text)
        clipboard?.setPrimaryClip(clip)

        Toast.makeText(this, "Deeplink Copied : $text", Toast.LENGTH_SHORT).show()
    }

    private fun fireDeeplink(deeplink: String) {
        try {
            startActivity(
                Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(deeplink)
                }
            )
            deepLinkViewModel.insert(DeepLinkData(deeplink, System.currentTimeMillis()))
            System.out.println("" + System.currentTimeMillis())
        } catch (exception: ActivityNotFoundException) {
            Toast.makeText(this, "No App found to handle this deeplink", Toast.LENGTH_LONG).show()
        } catch (exception: Exception) {
            Toast.makeText(this, "Please enter the correct deeplink", Toast.LENGTH_LONG).show()
        }
    }

}