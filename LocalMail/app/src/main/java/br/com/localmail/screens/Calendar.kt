package br.com.localmail.screens

import android.graphics.Paint
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import java.util.Calendar

private const val CALENDAR_ROWS = 6
private const val CALENDAR_COLUMNS = 7

@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    calendarInput: List<CalendarInput>,
    onDayClick: (Int) -> Unit,
    strokeWidth: Float = 15f,
    month: String,
    year: Int
) {
    var canvasSize by remember {
        mutableStateOf(Size.Zero)
    }
    var clickAnimationOffset by remember {
        mutableStateOf(Offset.Zero)
    }
    var animationRadius by remember {
        mutableStateOf(0f)
    }
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "$month $year",
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            fontSize = 40.sp
        )
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(true) {
                    detectTapGestures(
                        onTap = { offset ->
                            val column =
                                (offset.x / canvasSize.width * CALENDAR_COLUMNS).toInt()
                            val row = (offset.y / canvasSize.height * CALENDAR_ROWS).toInt()
                            val day = column + (row) * CALENDAR_COLUMNS
                            if (day < calendarInput.size && calendarInput[day].day != 0) {
                                onDayClick(calendarInput[day].day)
                                clickAnimationOffset = offset
                                scope.launch {
                                    animate(0f, 225f, animationSpec = tween(300)) { value, _ ->
                                        animationRadius = value
                                    }
                                }
                            }

                        }
                    )
                }
        ) {
            val canvasHeight = size.height
            val canvasWidth = size.width
            canvasSize = Size(canvasWidth, canvasHeight)
            val ySteps = canvasHeight / CALENDAR_ROWS
            val xSteps = canvasWidth / CALENDAR_COLUMNS

            val column = (clickAnimationOffset.x / canvasSize.width * CALENDAR_COLUMNS).toInt()
            val row = (clickAnimationOffset.y / canvasSize.height * CALENDAR_ROWS).toInt()

            val path = Path().apply {
                moveTo((column) * xSteps, (row) * ySteps)
                lineTo((column + 1) * xSteps, (row) * ySteps)
                lineTo((column + 1) * xSteps, (row + 1) * ySteps)
                lineTo((column) * xSteps, (row + 1) * ySteps)
                close()
            }

            clipPath(path) {
                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(
                            Color.Red.copy(0.8f),
                            Color.Red.copy(0.2f),
                        ),
                        center = clickAnimationOffset,
                        radius = animationRadius + 0.1f
                    ),
                    radius = animationRadius + 0.1f,
                    center = clickAnimationOffset
                )
            }

            drawRoundRect(
                Color.Red,
                cornerRadius = CornerRadius(25f, 25f),
                style = Stroke(
                    width = strokeWidth
                )
            )

            for (i in 1 until CALENDAR_ROWS) {
                drawLine(
                    color = Color.Red,
                    start = Offset(0f, ySteps * i),
                    end = Offset(canvasWidth, ySteps * i),
                    strokeWidth = strokeWidth
                )

            }
            for (i in 1 until CALENDAR_COLUMNS) {
                drawLine(
                    color = Color.Red,
                    start = Offset(xSteps * i, 0f),
                    end = Offset(xSteps * i, canvasHeight),
                    strokeWidth = strokeWidth
                )

            }
            val textHeight = 17.dp.toPx()
            for (i in calendarInput.indices) {
                if (calendarInput[i].day != 0) {
                    val textPositionX = xSteps * (i % CALENDAR_COLUMNS) + xSteps / 2
                    val textPositionY = ySteps * (i / CALENDAR_COLUMNS) + ySteps / 2 + textHeight / 4
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            "${calendarInput[i].day}",
                            textPositionX,
                            textPositionY,
                            Paint().apply {
                                textAlign = Paint.Align.CENTER
                                textSize = textHeight
                                color = Color.White.toArgb()
                                isFakeBoldText = true
                            }
                        )
                    }
                }
            }
        }
    }
}

data class CalendarInput(
    val day: Int,
    val hour: String, // campo para a hora
    val toDos: List<String> = emptyList() // campo para compromissos
)

@Composable
fun SuperCalendar(navController: NavController) {
    val calendarInputList by remember { mutableStateOf(createCalendarList(9, 2023)) }
    var clickedCalendarElem by remember { mutableStateOf<CalendarInput?>(null) }
    var month by remember { mutableStateOf(9) }
    var year by remember { mutableStateOf(2023) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 32.dp)
            ) {
                Button(
                    onClick = {
                        if (month == 1) {
                            month = 12
                            year--
                        } else {
                            month--
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Red
                    )
                ) {
                    Text(text = "<")
                }
                Text(
                    text = "${getMonthName(month)} $year",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Button(
                    onClick = {
                        if (month == 12) {
                            month = 1
                            year++
                        } else {
                            month++
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Red
                    )
                ) {
                    Text(text = ">")
                }
            }
            Calendar(
                calendarInput = createCalendarList(month, year),
                onDayClick = { day -> clickedCalendarElem = calendarInputList.first { it.day == day } },
                month = getMonthName(month),
                year = year,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .aspectRatio(1.3f)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                clickedCalendarElem?.toDos?.forEach {
                    Text(
                        if (it.contains("Day")) it else "-$it",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = if (it.contains("Day")) 25.sp else 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Compromisso:",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Restante do conteúdo do calendário

                    Spacer(modifier = Modifier.height(78.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Button(
                            onClick = { navController.navigate("inbox") },
                            modifier = Modifier
                                .height(60.dp)
                                .width(140.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Red,
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "Voltar",
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

    }
}

private fun createCalendarList(month: Int, year: Int): List<CalendarInput> {
    val calendar = Calendar.getInstance()
    calendar.set(year, month - 1, 1)
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1
    val calendarInputs = mutableListOf<CalendarInput>()

    for (i in 0 until firstDayOfWeek) {
        calendarInputs.add(CalendarInput(0, "", emptyList())) // adiciona vazio para hora e compromisso
    }
    for (i in 1..daysInMonth) {
        // adiciona exemplos de hora e compromisso
        calendarInputs.add(
            CalendarInput(
                i,
                "12:00 PM", // hora inicial padrão
                toDos = listOf(
                    "Day $i:",
                    "2 p.m. Buying groceries",
                    "4 p.m. Meeting with Larissa"
                )
            )
        )
    }
    return calendarInputs
}

private fun getMonthName(month: Int): String {
    return when (month) {
        1 -> "January"
        2 -> "February"
        3 -> "March"
        4 -> "April"
        5 -> "May"
        6 -> "June"
        7 -> "July"
        8 -> "August"
        9 -> "September"
        10 -> "October"
        11 -> "November"
        12 -> "December"
        else -> ""
    }
}

