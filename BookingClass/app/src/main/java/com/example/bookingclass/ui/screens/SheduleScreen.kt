package com.example.bookingclass.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bookingclass.model.Shedule

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SheduleScreen() {

    Column(modifier = Modifier.padding(16.dp)) {
        DatePicker()
        val cardItems = listOf(
            "Title 1" to "Subtitle 1",
            "Title 2" to "Subtitle 2",
            "Title 3" to "Subtitle 3"
        )
    }
}


@Composable
fun CardList(events: List<Shedule> ) {

    var selectedEvent by remember { mutableStateOf<Shedule?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    LazyColumn {
        items(events) { event ->

            EventCard(event = event, onItemClick = { selectedProduct ->
                selectedEvent = selectedProduct
                showDialog = true

            })
        }
    }

    selectedEvent?.let { event ->
        ConfirmationDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onConfirm = {
                showDialog = false
            },
            event = event
        )
    }
}

@Composable
fun EventCard(event: Shedule, onItemClick: (Shedule) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(event) }
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            ),

        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = event.title, style = MaterialTheme.typography.titleMedium)
                Text(text = event.date.toString(), style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = event.time.toString(), style = MaterialTheme.typography.bodySmall)
            }
        }
    }

}


@Composable
fun ConfirmationDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    event: Shedule
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "¿Reservar horario?") },
            text = {
                Column {
                    Text(
                        text = event.title,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "Fecha: ${event.date}")
                    Text(text = "Hora: ${event.time}")
                }
            },
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text("Reservar")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Cancelar")
                }
            }
        )
    }
}


