package com.example.pokemonapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.pokemonapp.data.models.MyPokemon

@Composable
fun DialogConfirmationComponent(myPokemon: MyPokemon, onDismiss: ()-> Unit, onConfirm: (MyPokemon)-> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                onClick = { onDismiss() }
            ) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(myPokemon) }
            ) {
                Text(text = "OK")
            }
        },
        title = {
            Text(text = "Delete Confirmation")
        },
        text = {
            Text(text = "Are you sure want to delete ${myPokemon.name}?")
        },

        )
}