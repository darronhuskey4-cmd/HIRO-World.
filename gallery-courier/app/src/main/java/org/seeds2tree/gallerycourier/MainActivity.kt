package org.seeds2tree.gallerycourier

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private val selected = mutableListOf<Uri>()
    private lateinit var status: TextView
    private val pickCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 64, 40, 40)
        }
        layout.addView(TextView(this).apply {
            text = "Zork Gallery Courier"
            textSize = 26f
        })
        layout.addView(TextView(this).apply {
            text = "Select photos, then send them using Android's secure Share menu. Originals stay untouched."
            textSize = 16f
        })
        status = TextView(this).apply { text = "No pictures selected"; textSize = 18f }
        layout.addView(status)
        layout.addView(Button(this).apply {
            text = "SELECT PICTURES"
            setOnClickListener { pickImages() }
        })
        layout.addView(Button(this).apply {
            text = "SEND TO ZORK"
            setOnClickListener { shareImages() }
        })
        layout.setOnApplyWindowInsetsListener { view, insets ->
            view.setPadding(40 + insets.systemWindowInsetLeft, 40 + insets.systemWindowInsetTop,
                40 + insets.systemWindowInsetRight, 40 + insets.systemWindowInsetBottom)
            insets
        }
        setContentView(layout)
    }

    private fun pickImages() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        startActivityForResult(intent, pickCode)
    }

    @Deprecated("Legacy activity result used for broad Android compatibility")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != pickCode || resultCode != RESULT_OK || data == null) return
        selected.clear()
        val clip = data.clipData
        if (clip != null) {
            for (i in 0 until clip.itemCount) selected.add(clip.getItemAt(i).uri)
        } else data.data?.let { selected.add(it) }
        status.text = "${selected.size} picture(s) selected"
    }

    private fun shareImages() {
        if (selected.isEmpty()) {
            Toast.makeText(this, "Select pictures first", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
            type = "image/*"
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList(selected))
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            val clip = ClipData.newUri(contentResolver, "Selected photos", selected.first())
            for (uri in selected.drop(1)) clip.addItem(ClipData.Item(uri))
            clipData = clip
        }
        startActivity(Intent.createChooser(intent, "Send pictures via"))
    }
}
