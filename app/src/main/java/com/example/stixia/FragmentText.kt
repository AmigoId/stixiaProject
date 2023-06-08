package com.example.stixia

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.example.stixia.databinding.FragmentTextBinding
import com.parse.*
import kotlin.random.Random

class FragmentText : Fragment() {
    val dataModel: ModulMassage by activityViewModels()
    var countSub: Int = 0
    var idPoem: String = ""
    var morePoem = 0
    lateinit var bindingClass: FragmentTextBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    var arrOfCountView: ArrayList<Boolean> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingClass = FragmentTextBinding.inflate(layoutInflater)
        var keyObserv: Int = 0
        var indexPoem: Int = 0
        // val databaseHandler: DatabaseHandler = DatabaseHandler(requireContext())
        var poems: ArrayList<poem> = arrayListOf()
        if (poems.size == 0) {
            val query = ParseQuery.getQuery<ParseObject>("poems")
            query.orderByDescending("createdAt")
            query.findInBackground { it, e ->
                if (e == null) {
                    var key2: Int = 0
                    for (i in 0..it.size - 1) {
                        //Log.d("антидубль через main", "${poems.size}")
                        if (i < (it.size - 100)) {
                            break
                        }
                        var temp = poem(
                            it[i].objectId,
                            it[i].get("imageId").toString().toInt(),
                            it[i].get("name").toString(),
                            it[i].get("text").toString(),
                            it[i].get("bio").toString(),
                            it[i].get("countLike").toString().toInt(),
                            it[i].get("countView").toString().toInt(),
                            it[i].get("idAuthor").toString()
                        )

                        if ((poems.size == 0 && key2 == 0) || (key2 == 1)) {
                            poems.add(temp)
                            arrOfCountView.add(false)
                            key2 = 1
                        } else {
                            break
                        }

                        indexPoem = 0           // MainVar
                        if (keyObserv == 0) {
                            viewPoem(poems, indexPoem)
                        }
                    }
                    Log.d("myLog", poems.size.toString() + " " + indexPoem + " /size")
                } else {
                    Log.d("myLog", "Error ${e.message}")
                }
            }
        }
        //var authorSubList: List<AuthorSub> = databaseHandler.viewAuthorSub()
        //var authorLikeList: List<AuthorLike> = databaseHandler.viewAuthorLike()
        dataModel.messageToFTextId.observe(viewLifecycleOwner) {
            var addPoem = it
            keyObserv = 1

            var key = 0
            indexPoem = 0           // MainVar
            poems.add(indexPoem, addPoem)
            arrOfCountView.add(indexPoem, false)
            viewPoem(poems, indexPoem)
            val query = ParseQuery.getQuery<ParseObject>("poems")
            query.orderByDescending("createdAt")
            query.findInBackground { it, e ->
                if (e == null) {
                    var key3 = 0
                    for (i in (0..it.size - 1)) {
                        if (addPoem.id == it[i].objectId) {
                            continue
                        }
                        Log.d("обсерв poems.size", "${poems.size}")
                        if (i > 99) {
                            break
                        }

                        var temp = poem(
                            it[i].objectId,
                            it[i].get("imageId").toString().toInt(),
                            it[i].get("name").toString(),
                            it[i].get("text").toString(),
                            it[i].get("bio").toString(),
                            it[i].get("countLike").toString().toInt(),
                            it[i].get("countView").toString().toInt(),
                            it[i].get("idAuthor").toString()
                        )
                        poems.add(temp)
                        arrOfCountView.add(false)

                    }

                } else {
                    Log.d("myLog", "Error ${e.message}")
                }
            }
        }
        bindingClass.imageL.setOnClickListener {
            indexPoem = (indexPoem + 1) % poems.size
            viewPoem(poems, indexPoem)
        }

        bindingClass.imageR.setOnClickListener {
            if (indexPoem > 0)
                indexPoem--
            else
                indexPoem = poems.size - 1
            viewPoem(poems, indexPoem)
        }


        bindingClass.textChannelButton.setOnClickListener {
            var key2: Int = 0
            val query = ParseQuery.getQuery<ParseObject>("authorSub")
            query.whereEqualTo("idAuthor", ParseUser.getCurrentUser().objectId)
            query.findInBackground { it, e ->
                if (e == null) {
                    if (it.size != 0) {
                        for (i in 0..it.size - 1) {
                            if (poems[indexPoem].idAuthor == it[i].get("idBook")) {
                                key2 = 1
                                Log.d("cheker key", "${key2}")
                                Toast.makeText(
                                    requireContext(),
                                    "Подписка уже оформлена!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                break
                            }
                        }
                    }
                    Log.d("Size AuthorSub", it.size.toString() + " size")
                    if (key2 == 0) {
                        val todo = ParseObject("authorSub")
                        todo.put("idAuthor", ParseUser.getCurrentUser().objectId)
                        todo.put("idBook", poems[indexPoem].idAuthor)
                        todo.saveInBackground(SaveCallback { e ->
                            if (e == null) {
                                val tempAuthor = Author(                //Рудимент
                                    poems[indexPoem].idAuthor,
                                    0,
                                    "",
                                    "",
                                    "",
                                    ++countSub
                                )
                                updateDataAuthor(tempAuthor)
                                bindingClass.textChannelCountSub.text = countSub.toString()
                                Toast.makeText(
                                    requireContext(),
                                    "Подписка успешно оформлена",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Что то пошло не так.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.e("AuthorSubError", "${e.message}")
                            }
                        })
                    }
                } else {
                    Log.d("myLog", "Error ${e.message}")
                }

            }
            Log.d("cheker key 3", "${key2}")
        }



        bindingClass.textLikePoem.setOnClickListener {
            var key3: Int = 0

            val query = ParseQuery.getQuery<ParseObject>("authorLike")
            query.whereEqualTo("idAuthor", ParseUser.getCurrentUser().objectId)
            query.findInBackground { it, e ->
                if (e == null) {
                    if (it.size != 0) {
                        for (i in 0..it.size - 1) {
                            if (poems[indexPoem].id == it[i].get("idLike")) {
                                key3 = 1
                                Log.d("cheker key 3", "${key3}")
                                Toast.makeText(
                                    requireContext(),
                                    "Лайк уже поставлен!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                break
                            }
                        }
                    }
                    Log.d("Size AuthorLike", it.size.toString() + " size")
                    if (key3 == 0) {
                        val todo = ParseObject("authorLike")
                        todo.put("idAuthor", ParseUser.getCurrentUser().objectId)
                        todo.put("idLike", poems[indexPoem].id)
                        todo.saveInBackground(SaveCallback { e ->
                            if (e == null) {
                                poems[indexPoem].countLike++
                                viewPoem(poems, indexPoem)
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Oops - Try again later.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                    }
                } else {
                    Log.d("myLog", "Error")
                }
            }
            Log.d("cheker key 3", "${key3}")
        }

        //}
        bindingClass.goToChannelAuthor.setOnClickListener {
            idPoem = poems[indexPoem].idAuthor
            dataModel.messageToAcId.value = idPoem
            dataModel.messageToAc.value = "Open Author"
        }

        return bindingClass.root

    }

    companion object {

        fun newInstance() =
            FragmentText()

    }

    fun updateDataPoem(updatedPoem: poem) {
        val query = ParseQuery.getQuery<ParseObject>("poems") // Configure Query with our query.
        // adding a condition where our course name must be equal to the original course name
        query.whereEqualTo("objectId", updatedPoem.id)
        // in below method we are getting the unique id
        // of the course which we have to make update.
        query.getFirstInBackground(GetCallback<ParseObject>() { it, e ->
            // inside done method we check
            // if the error is null or not.
            if (e == null) {
                // if the error is null then we are getting
                // our object id in below line.
                var itID = it.objectId.toString()
                // after getting our object id we will
                // move towards updating our course.
                // calling below method to update our course.
                query.getInBackground(itID, GetCallback<ParseObject>() { object1, e ->
                    // in this method we are getting the
                    // object which we have to update.
                    if (e == null) {
                        // in below line we are adding new data
                        // to the object which we get from its id.
                        // on below line we are adding our data
                        // with their key value in our object.

                        object1.put("countLike", updatedPoem.countLike)
                        object1.put("countView", updatedPoem.countView)

                        object1.saveInBackground(SaveCallback { e ->
                            if (e == null) {
                                //Toast.makeText(requireContext(), "Note saved successfully!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Oops - Try again later.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                    }
                })
            }
        })
    }

    fun updateDataAuthor(updatedAuthor: Author) {
        Log.d("myLog", "Всё как нада")
        val query = ParseQuery.getQuery<ParseObject>("_User") // Configure Query with our query.
        // adding a condition where our course name must be equal to the original course name
        Log.d("myLog", "updatedAuthor.id:${updatedAuthor.id}")
        query.whereEqualTo("objectId", updatedAuthor.id)
        // in below method we are getting the unique id
        // of the course which we have to make update.
        query.getFirstInBackground(GetCallback<ParseObject>() { it, e ->
            // inside done method we check
            // if the error is null or not.
            if (e == null) {
                // if the error is null then we are getting
                // our object id in below line.
                var itID = it.objectId.toString()
                // after getting our object id we will
                // move towards updating our course.
                // calling below method to update our course.
                query.getInBackground(itID, GetCallback<ParseObject>() { object1, e ->
                    // in this method we are getting the
                    // object which we have to update.
                    if (e == null) {
                        // in below line we are adding new data
                        // to the object which we get from its id.
                        // on below line we are adding our data
                        // with their key value in our object.
                        Log.d("myLog", "updatedAuthor.countSub:${updatedAuthor.countSub}")
                        object1.put("countSub", updatedAuthor.countSub)
                        object1.saveInBackground(SaveCallback { e ->
                            if (e == null) {
                                Toast.makeText(
                                    requireContext(),
                                    "Note saved successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Oops - Try again later.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                    } else {
                        Log.d("myLog", "А теперь все плохо2")
                        Log.d("myLog", "${e.code}. ${e.message}")
                    }
                })
            } else {
                Log.d("myLog", "А теперь все плохо1")
                Log.d("myLog", "${e.code}. ${e.message}")
            }
        })

    }

    fun viewPoem(poems: ArrayList<poem>, indexPoem: Int) {
        val query2 = ParseUser.getQuery()
        query2.whereEqualTo("objectId", poems[indexPoem].idAuthor)
        query2.findInBackground { it2, e ->
            Log.d("size of Author", "${it2.size}")
            if (e == null) {
                if (arrOfCountView.size != 0 && (!arrOfCountView[indexPoem])) {
                    poems[indexPoem].countView++
                    arrOfCountView[indexPoem] = true
                }
                countSub = it2[0].get("countSub").toString().toInt();
                bindingClass.textChannelCountSub.text = countSub.toString();
                bindingClass.textChannelImage.setImageResource(
                    it2[0].get("imageId").toString().toInt()
                )
                bindingClass.textChannelName.text = it2[0].get("username").toString()
                bindingClass.TextCoutnOfLike.text = poems[indexPoem].countLike.toString()
                bindingClass.textcountOfView.text = poems[indexPoem].countView.toString()
                bindingClass.textBio.text = poems[indexPoem].bio
                bindingClass.imageView.setImageResource(poems[indexPoem].imageId)
                bindingClass.textName.text = poems[indexPoem].name
                bindingClass.textText.text = poems[indexPoem].text
                idPoem = poems[indexPoem].idAuthor
                dataModel.messageToAcId.value = idPoem

                Log.d("size of poems in list", "${poems.size}")
                updateDataPoem(poems[indexPoem])
            }
            Log.d("myLog", it2.size.toString() + " size")
        }

    }
}