package com.myapplication.healthfitnesstrackerapp



import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {
    private lateinit var syncButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        syncButton = findViewById(R.id.btnSync)

        syncButton.setOnClickListener {
            // Simulate data sync
            Toast.makeText(this, "Data Synced Successfully", Toast.LENGTH_SHORT).show()
        }
    }
}
