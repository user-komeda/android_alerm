package com.example.androidAlarm.domain.usecase

interface BaseNoParamUseCase<out Result> {

    suspend operator fun invoke(): Result
}
