package com.company.fetchexercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.company.fetchexercise.models.Item
import com.company.fetchexercise.ui.theme.FetchExerciseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FetchExerciseTheme(dynamicColor = false) {
                MainScreen()
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 320)
@Composable
fun PreviewEasterEggDialog() {
    FetchExerciseTheme {
        EasterEggDialog(onDismiss = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListIdCardExpanded() {
    FetchExerciseTheme {
        ListIdCard(
            listId = 42,
            isExpanded = true,
            onClick = {},
            items = listOf(
                Item(listId = 42, id = 1, name = "Item 1"),
                Item(listId = 42, id = 2, name = "Item 2")
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListIdCardCollapsed() {
    FetchExerciseTheme {
        ListIdCard(
            listId = 42,
            isExpanded = false,
            onClick = {},
            items = listOf(
                Item(listId = 42, id = 1, name = "Item 1"),
                Item(listId = 42, id = 2, name = "Item 2")
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItemList() {
    FetchExerciseTheme {
        ItemList(
            items = listOf(
                Item(listId = 1, id = 1, name = "First Item"),
                Item(listId = 1, id = 2, name = "Second Item"),
                Item(listId = 1, id = 3, name = "Third Item")
            )
        )
    }
}