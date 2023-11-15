package com.example.pokemonapp.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pokemonapp.data.models.MyPokemon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetComponent(myPokemon: MyPokemon, onDismiss: ()-> Unit, onDelete: (MyPokemon)-> Unit, onEdit: (MyPokemon)-> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = {BottomSheetDefaults.DragHandle()}) {
        Column() {
            Row(
                modifier = Modifier.clickable { onEdit(myPokemon) }.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Change Nickname",
                )
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "Change Nickname",
                    tint = Color.Yellow)
            }
            Row(
                modifier = Modifier.clickable { onDelete(myPokemon) }.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Delete Pokemon",
                )
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete Pokemon",
                    tint = Color.Red)
            }
        }
    }
}
