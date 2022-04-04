package com.example.mandatoryassignment

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.navigation.ui.onNavDestinationSelected
import com.example.mandatoryassignment.databinding.ActivityMainBinding
import com.example.mandatoryassignment.models.Item
import com.example.mandatoryassignment.models.ItemsViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val itemsViewModel: ItemsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            showDialog()
        }

        itemsViewModel.updateMessageLiveData.observe(this) { message ->
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.loginfragment -> item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment_content_main))
            R.id.action_signout -> {
                if (Firebase.auth.currentUser != null) {
                    Firebase.auth.signOut()
                    val navController = findNavController(R.id.nav_host_fragment_content_main)
                    navController.popBackStack(R.id.FirstFragment, false)
                    // https://developer.android.com/codelabs/android-navigation#6
                    Snackbar.make(binding.root, "You are now signed out", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(binding.root, "Cannot sign out", Snackbar.LENGTH_LONG).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)


        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun showDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Add Item")

        val layout = LinearLayout(this@MainActivity)
        layout.orientation = LinearLayout.VERTICAL

        val titleInputField = EditText(this)
        titleInputField.hint = "Title"
        titleInputField.inputType = InputType.TYPE_CLASS_TEXT
        layout.addView(titleInputField)

        val sellerInputField = EditText(this)
        sellerInputField.hint = "Seller"
        sellerInputField.inputType = InputType.TYPE_CLASS_TEXT
        layout.addView(sellerInputField)

        val descriptionInputField = EditText(this)
        descriptionInputField.hint = "Description"
        descriptionInputField.inputType = InputType.TYPE_CLASS_TEXT
        layout.addView(descriptionInputField)

        val bodyInputField = EditText(this)
        bodyInputField.hint = "Price"
        bodyInputField.inputType =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        layout.addView(bodyInputField)

        builder.setView(layout)

        builder.setPositiveButton("OK") {dialog, which ->
            val title = titleInputField.text.toString().trim()
            val seller = sellerInputField.text.toString().trim()
            val description = descriptionInputField.text.toString().trim()
            val price = bodyInputField.text.toString().trim()
            when {
                title.isEmpty() ->
                    Snackbar.make(binding.root, "No title", Snackbar.LENGTH_LONG).show()
                seller.isEmpty() ->
                    Snackbar.make(binding.root, "No seller", Snackbar.LENGTH_LONG).show()
                description.isEmpty() ->
                    Snackbar.make(binding.root, "No description", Snackbar.LENGTH_LONG).show()
                price.isEmpty() ->
                    Snackbar.make(binding.root, "No price", Snackbar.LENGTH_LONG).show()
                else -> {
                    val item = Item(title, description, price.toInt(),seller)
                    itemsViewModel.add(item)
                }
            }
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder.show()
    }
}