package com.unilever.julia.firebase.database

import com.google.firebase.database.*
import com.google.gson.Gson
import com.unilever.julia.firebase.exception.NotFoundDataException
import io.reactivex.Observable

object DatabaseRealtime {

    private var mDatabase = FirebaseDatabase.getInstance().reference

    fun <T> getObservableValue(gson: Gson, path: String, classOfT: Class<T>): Observable<T> {
        return Observable.create { subscriber ->
            mDatabase.child(path)
                .addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        try {
                            if (!dataSnapshot.exists()) {
                                throw NotFoundDataException()
                            }

                            val result = dataSnapshot.value
                            val json = gson.toJson(result)
                            val model = gson.fromJson(json, classOfT)

                            subscriber.onNext(model)
                            subscriber.onComplete()
                        } catch (e : Throwable) {
                            subscriber.onError(e)
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        subscriber.onError(databaseError.toException())
                    }
                })
        }
    }
}