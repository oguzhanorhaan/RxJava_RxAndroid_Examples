package com.example.basics.examples.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.basics.R;
import com.example.basics.examples.Models.Note;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**----------  Maybe & MaybeObserver ----------------

 * Consider an example getting a note from db using ID
 * There is possibility of not finding the note by ID in the db
 * In this situation, MayBe can be used
 * -
 * Maybe : MaybeObserver
 */

public class Example6Activity extends AppCompatActivity {

    private static final String TAG = Example6Activity.class.getSimpleName();
    private Disposable disposable;

    private void subscribe(){
        Maybe<Note> noteObservable = getNoteObservable();

        MaybeObserver<Note> noteObserver = getNoteObserver();

        noteObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(noteObserver);
    }

    private MaybeObserver<Note> getNoteObserver() {
        return new MaybeObserver<Note>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(Note note) {
                Log.e(TAG, "onSuccess: " + note.getNote());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }
        };
    }

    /**
     * Optional can be retrieved optionally, in this example it is retrieved
     */
    private Maybe<Note> getNoteObservable() {
        return Maybe.create(new MaybeOnSubscribe<Note>() {
            @Override
            public void subscribe(MaybeEmitter<Note> emitter) throws Exception {
                Note note = new Note(1, "Call brother!");
                if (!emitter.isDisposed()) {
                    emitter.onSuccess(note);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example6);
        subscribe();
    }
}
