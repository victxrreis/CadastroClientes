package br.com.victor.cadastroclientes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.victor.cadastroclientes.data.Cliente
import br.com.victor.cadastroclientes.ui.ClienteViewModel
import br.com.victor.cadastroclientes.ui.theme.CadastroClientesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CadastroClientesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ClienteScreen()
                }
            }
        }
    }
}

@Composable
fun ClienteScreen(clienteViewModel: ClienteViewModel = viewModel()) {
    val clientes by clienteViewModel.todosClientes.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        FormularioCliente(onClienteAdicionado = { cliente ->
            clienteViewModel.insert(cliente)
        })
        Spacer(modifier = Modifier.height(24.dp))
        ListaDeClientes(clientes = clientes)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioCliente(onClienteAdicionado: (Cliente) -> Unit) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Cadastro de Clientes", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome do Cliente") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail do Cliente") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (nome.isNotBlank() && email.isNotBlank()) {
                    onClienteAdicionado(Cliente(nome = nome, email = email))
                    nome = ""
                    email = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("ADICIONAR CLIENTE")
        }
    }
}

@Composable
fun ListaDeClientes(clientes: List<Cliente>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(clientes) { cliente ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ID: ${cliente.id}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(0.2f)
                    )
                    Column(modifier = Modifier.weight(0.8f)) {
                        Text(text = cliente.nome, style = MaterialTheme.typography.titleMedium)
                        Text(text = cliente.email, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}