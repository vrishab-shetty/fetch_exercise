package com.company.fetchexercise

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.*
import com.company.fetchexercise.models.Item
import com.company.fetchexercise.models.ListUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: ItemViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    var showEasterEgg by remember { mutableStateOf(false) }
    var expandedListId by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Fetch Exercise",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.pointerInput(Unit) {
                            detectTapGestures(onLongPress = {
                                showEasterEgg = true
                            })
                        }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when (uiState) {
                is ListUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is ListUiState.Error -> {
                    val message = (uiState as ListUiState.Error).message
                    ErrorView(
                        message = message,
                        onRetry = { viewModel.retryFetch() }
                    )
                }
                is ListUiState.Success -> {
                    val groupedItems = (uiState as ListUiState.Success).groupedItems
                    LazyColumn(
                        contentPadding = PaddingValues(12.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        groupedItems.forEach { (listId, items) ->
                            item {
                                ListIdCard(
                                    listId = listId,
                                    isExpanded = expandedListId == listId,
                                    onClick = {
                                        expandedListId = if (expandedListId == listId) null else listId
                                    },
                                    items = items
                                )
                            }
                        }
                    }
                }
            }

            if (showEasterEgg) {
                EasterEggDialog(onDismiss = { showEasterEgg = false })
            }
        }
    }
}

@Composable
fun ListIdCard(
    listId: Int,
    isExpanded: Boolean,
    onClick: () -> Unit,
    items: List<Item>,
) {
    Surface(
        tonalElevation = 2.dp,
        shadowElevation = 4.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "List ID: $listId",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Icon(
                    imageVector = if (isExpanded)
                        Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                ItemList(items)
            }
        }
    }
}

@Composable
fun ItemList(items: List<Item>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items.forEach { item ->
            Card(
                shape = MaterialTheme.shapes.small,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.name.orEmpty(),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

@Composable
fun ErrorView(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
fun EasterEggDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Aww 🐾")
            }
        },
        title = { Text("🐶 You've fetched the secret!") },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Hello Fetch Engineer!\n\nThanks for reviewing my work.")
                Spacer(modifier = Modifier.height(16.dp))
                val composition by rememberLottieComposition(LottieCompositionSpec.Asset("dog_fetch.json"))
                LottieAnimation(
                    composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.height(150.dp)
                )
            }
        }
    )
}
