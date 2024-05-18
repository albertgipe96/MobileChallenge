package com.example.mobilechallenge.cabifystore.domain.usecases

abstract class UseCase<Q: UseCase.RequestValues, P: UseCase.ResponseValue> {

    abstract operator suspend fun invoke(requestValues: Q? = null): P

    interface RequestValues // Data passed to a request
    interface ResponseValue // Data received from a request
}