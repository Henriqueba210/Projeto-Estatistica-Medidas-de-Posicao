package com.br.projetoEstatistica

import java.io.IOException

class Main {

    companion object {
        private fun String?.toDoubleValue() = this?.toDoubleOrNull() ?: 0.0
        private fun String?.toIntValue() = this?.toIntOrNull() ?: 0

        private fun limparConsole() {
            try {
                if (System.getProperty("os.name").contains("Windows"))
                    ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor()
                else
                    Runtime.getRuntime().exec("clear")
            } catch (ex: IOException) {
            } catch (ex: InterruptedException) {
            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            var entrada: String? = ""
            val numeros: MutableList<Double> = mutableListOf()
            var nElementos = 0
            val separadorLinha = System.getProperty("line.separator")
            while (entrada != "4") {
                println("-------------------- Medidias de Posição --------------------$separadorLinha")
                println("${separadorLinha}Informe o código da operação para executa-la:$separadorLinha")
                println("[1] Media")
                println("[2] Moda")
                println("[3] Mediana")
                println("[4] Finalizar o programa")
                println("-------------------------------------------------------------")
                entrada = readLine()

                while (nElementos == 0) {
                    println("${separadorLinha}Informe o número de elementos do conjunto de dados")
                    val input = readLine()
                    nElementos = input.toIntValue()
                    if (nElementos == 0)
                        println("Insira um número válido")
                }

                for (i in 1..nElementos) {
                    var input = 0.0
                    while (input == 0.0) {
                        println("${separadorLinha}Insira um número para o conjunto de dados")
                        input = readLine().toDoubleValue()
                        if (input == 0.0)
                            println("Insira um número válido")
                    }
                    numeros.add(i - 1, input)
                }

                when (entrada) {
                    "1" -> {
                        val media = numeros.average()
                        println(
                            "${separadorLinha}A média do conjunto ${numeros.joinToString(
                                prefix = "[",
                                postfix = "]"
                            )} é $media$separadorLinha"
                        )
                    }
                    "2" -> {
                        val sortedByFreq = numeros.groupBy { it }.entries.sortedByDescending { it.value.size }
                        val maxFreq = sortedByFreq.first().value.size
                        val modes = sortedByFreq.takeWhile { it.value.size == maxFreq }
                        val conjunto = numeros.joinToString(
                            prefix = "[",
                            postfix = "]"
                        )
                        if (modes.size == 1)
                            println("A moda do conjunto $conjunto é ${modes.first().key} e sua frequência é $maxFreq")
                        else {
                            print("o conjunto $conjunto possui ${modes.size} modas com uma frequencia de $maxFreq, sendo elas : ")
                            println(modes.map { it.key }.joinToString(", "))
                        }
                    }

                    "3" -> {
                        val mediana = numeros.sorted().let { (it[it.size / 2] + it[(it.size - 1) / 2]) / 2 }
                        println(
                            "${separadorLinha}A mediana do conjunto  ${numeros.joinToString(
                                prefix = "[",
                                postfix = "]"
                            )} é $mediana"
                        )

                    }
                    else -> {
                        if (entrada != "4")
                            println("opção invalida")
                    }
                }
                println("Aperte enter para continuar")
                readLine()
                numeros.clear()
                nElementos = 0
                limparConsole()
            }
        }
    }
}