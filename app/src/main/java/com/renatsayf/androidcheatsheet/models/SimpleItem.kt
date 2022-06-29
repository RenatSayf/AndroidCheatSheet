package com.renatsayf.androidcheatsheet.models


//region Hint. Create the model of recycler view item
data class SimpleItem(
    val header: String,
    val content: String
) {

    companion object {

        //region The mock data for display
        fun getMockData(): List<SimpleItem> {

            val mockList = mutableListOf<SimpleItem>()
            repeat(20) { index ->

                mockList.add(
                    SimpleItem(
                        header = "Header $index",
                        content = """Text $index. Lorem ipsum dolor sit amet. Cum autem pariatur et enim 
                            |voluptatum 33 minus porro et temporibus illo ut porro doloremque. 
                            |Ad molestiae vero et rerum consequatur id maiores sequi. 
                            |In unde maiores ea officiis voluptatum eos incidunt repellendus.""".trimMargin()
                    )
                )
            }
            return mockList
        }
        //endregion
    }
}
//endregion
