package com.example.mushtool.Recetas

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter


@Composable
fun CardCustum(_recetaF: DataRecetasF, navController: NavHostController) {
    var visible by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(100)
                )
                .padding(vertical = 8.dp, horizontal = 16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .alpha(1.0f)
            ) {
                Box(
                    modifier = Modifier

                        .height(100.dp)
                        .width(250.dp)
                        .padding(8.dp)
                        .scale(if (visible) 2.5f else 1.5f),
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = rememberAsyncImagePainter(_recetaF.imagen),
                        contentDescription = "Soy seta1 ",
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    visible = !visible
                }) {
                    Icon(
                        modifier = Modifier.background(color = Color.LightGray),
                        imageVector = if (visible) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Icon"

                    )
                }

                AnimatedVisibility(

                    visible = visible,
                    enter = fadeIn() + slideInVertically(initialOffsetY = { fullHeight -> fullHeight }) + expandVertically(),
                    exit = fadeOut() + slideOutVertically(targetOffsetY = { fullHeight -> fullHeight }) + shrinkVertically()
                ) {
                    Text(
                        _recetaF.titulo,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                                color = Color.Black

                    )

                }
                    AnimatedVisibility(
                        modifier = Modifier
                            .align(Alignment.End),
                        visible = visible,
                        enter = fadeIn() + scaleIn(),
                        exit = fadeOut() + scaleOut()
                    ) {
                        Column {
                            Text(
                                //"Tipo: " + seta.tipo,
                                buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black
                                        )
                                    ) {
                                        append("INGREDIENTE: ")
                                    }
                                    withStyle(style = SpanStyle(color = Color.Black)) {
                                        append(_recetaF.ingrediente)
                                    }
                                }
                            )
                            Text(
                                buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black
                                        )
                                    ) {
                                        append("DESCRIPCIÓN: ")
                                    }
                                    append(_recetaF.descripcion)
                                },
                            )
                        }
                    }

                }

            }

        }
    }

