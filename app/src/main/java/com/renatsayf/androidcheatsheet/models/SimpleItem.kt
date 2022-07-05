package com.renatsayf.androidcheatsheet.models

import com.renatsayf.androidcheatsheet.data.db.room.java.ArticleEntity


//region Hint. Create the model of recycler view item
data class SimpleItem(
    val header: String,
    val content: String
) : ArticleEntity() {

    companion object {

        //region The mock data for display
        fun getMockData(): List<ArticleEntity> {

            val mockList = mutableListOf<ArticleEntity>()
            repeat(20) { index ->

                mockList.add(
                    ArticleEntity().apply {
                        header = "Header ${index + 1}"
                        content =
                            """Text ${index + 1}. Lorem ipsum dolor sit amet. Cum autem pariatur et enim 
                            |voluptatum 33 minus porro et temporibus illo ut porro doloremque. 
                            |Ad molestiae vero et rerum consequatur id maiores sequi. 
                            |In unde maiores ea officiis voluptatum eos incidunt repellendus.""".trimMargin()
                    }
                )
            }
            return mockList
        }
        //endregion
    }
}
//endregion
