package com.malakezzat.re7letelkalemat.Model

import android.os.Parcel
import android.os.Parcelable


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word(
    @PrimaryKey val word: String,
    val meaning: String,
    val exampleSentence: String
) : Parcelable {
    // Reading data from the parcel
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",   // Handle potential nulls
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    // Describe any special content types (file descriptors, etc.)
    override fun describeContents(): Int {
        return 0
    }

    // Writing data to the parcel
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(word)
        dest.writeString(meaning)
        dest.writeString(exampleSentence)
    }


    // Parcelable.Creator implementation to generate instances from Parcel
    companion object CREATOR : Parcelable.Creator<Word> {
        override fun createFromParcel(parcel: Parcel): Word {
            return Word(parcel)
        }

        override fun newArray(size: Int): Array<Word?> {
            return arrayOfNulls(size)
        }
    }
}


val wordsList = listOf(
    Word(
        word = "كلمة",
        meaning = "وحدة اللغة التي تتكون من حروف وتستخدم في التواصل.",
        exampleSentence = "الكلمة هي أساس التواصل بين الناس."
    ),
    Word(
        word = "مدينة",
        meaning = "منطقة سكنية كبيرة يعيش فيها العديد من الناس وتتوفر فيها خدمات كثيرة.",
        exampleSentence = "الرياض هي مدينة كبيرة في المملكة العربية السعودية."
    ),
    Word(
        word = "ثقافة",
        meaning = "مجموع العادات والتقاليد والمعارف التي تميز شعباً أو مجتمعاً معيناً.",
        exampleSentence = "الثقافة العربية تمتاز بالغنى والتنوع."
    ),
    Word(
        word = "سفر",
        meaning = "الانتقال من مكان إلى آخر، عادةً لمسافات طويلة.",
        exampleSentence = "أحب السفر لاستكشاف أماكن جديدة حول العالم."
    ),
    Word(
        word = "طعام",
        meaning = "ما يأكله الإنسان للبقاء على قيد الحياة وتلبية احتياجاته الغذائية.",
        exampleSentence = "الطعام اللبناني مشهور بمذاقه المميز."
    ),
    Word(
        word = "تحدي",
        meaning = "مهمة صعبة تحتاج إلى جهد لتحقيقها.",
        exampleSentence = "تعلم اللغة العربية قد يكون تحدياً ممتعاً."
    ),
    Word(
        word = "تعلم",
        meaning = "اكتساب المعرفة أو المهارات من خلال الدراسة أو الخبرة.",
        exampleSentence = "التعلم المستمر يساعد على تطوير الذات وتحقيق النجاح."
    ),
    Word(
        word = "معلم",
        meaning = "شخص يدرّس ويقدم المعرفة للآخرين.",
        exampleSentence = "المعلم هو الشخص الذي ينير طريق العلم للطلاب."
    ),
    Word(
        word = "مغامرة",
        meaning = "تجربة مثيرة تنطوي على المخاطر أو الاستكشاف.",
        exampleSentence = "كانت الرحلة إلى الجبال مغامرة لا تُنسى."
    ),
    Word(
        word = "تاريخ",
        meaning = "الأحداث التي وقعت في الماضي وتؤثر على الحاضر.",
        exampleSentence = "تاريخ المدن العربية مليء بالقصص والحضارات."
    ),
    Word(
        word = "لغة",
        meaning = "وسيلة للتواصل بين البشر من خلال الكلمات.",
        exampleSentence = "اللغة العربية هي واحدة من أقدم اللغات وأكثرها ثراءً."
    ),
    Word(
        word = "حضارة",
        meaning = "الإنجازات الثقافية والتقنية التي حققتها مجموعة من الناس على مر الزمن.",
        exampleSentence = "الحضارة الإسلامية أثرت بشكل كبير على تاريخ العالم."
    ),
    Word(
        word = "رحلة",
        meaning = "الانتقال من مكان إلى آخر بغرض الترفيه أو التعلم أو العمل.",
        exampleSentence = "كانت الرحلة إلى مراكش مليئة بالمغامرات والمفاجآت."
    ),
    Word(
        word = "تعامل",
        meaning = "كيفية التصرف أو التواصل مع الآخرين في مواقف معينة.",
        exampleSentence = "التعامل مع الناس بلطف واحترام يبني علاقات قوية."
    ),
    Word(
        word = "معلم سياحي",
        meaning = "مكان معروف ويزوره السياح بسبب أهميته التاريخية أو الجمالية.",
        exampleSentence = "الأهرامات في مصر تُعد من أشهر المعالم السياحية في العالم."
    ),Word(
        word = "تراث",
        meaning = "الموروثات الثقافية والفنية التي تنتقل عبر الأجيال.",
        exampleSentence = "التراث العربي غني بالفنون والموسيقى التقليدية."
    ),
    Word(
        word = "فن",
        meaning = "الإبداع والتعبير عن المشاعر والأفكار من خلال أشكال مختلفة مثل الرسم والموسيقى.",
        exampleSentence = "الفن العربي الإسلامي يتميز بالتفاصيل الهندسية الجميلة."
    ),
    Word(
        word = "معنى",
        meaning = "الفكرة أو المفهوم الذي تعبر عنه كلمة أو جملة.",
        exampleSentence = "معنى هذه الكلمة يرتبط بالثقافة المحلية."
    ),
    Word(
        word = "مهرجان",
        meaning = "حدث اجتماعي أو ثقافي يُقام للاحتفال بمناسبة معينة.",
        exampleSentence = "مهرجان الجنادرية في السعودية يعكس التراث الثقافي للمملكة."
    ),
    Word(
        word = "أسطورة",
        meaning = "قصة تقليدية تتضمن شخصيات خارقة أو أحداث غير عادية وتروي ثقافة شعب معين.",
        exampleSentence = "أساطير الأندلس تعكس تاريخاً غنياً ومليئاً بالخيال."
    ),
    Word(
        word = "سوق",
        meaning = "مكان يجتمع فيه الناس لبيع وشراء البضائع.",
        exampleSentence = "سوق مراكش يعتبر واحدًا من أشهر الأسواق التقليدية في العالم العربي."
    ),
    Word(
        word = "ضيافة",
        meaning = "الترحيب والاعتناء بالزوار أو الضيوف.",
        exampleSentence = "الضيافة العربية معروفة بالكرم والترحيب الحار."
    ),
    Word(
        word = "قصيدة",
        meaning = "نص شعري يتضمن تعبيرات فنية موزونة ومقفى.",
        exampleSentence = "القصائد العربية القديمة تعكس جمال اللغة والفكر."
    ),
    Word(
        word = "سياحة",
        meaning = "النشاط الذي يقوم به الشخص بزيارة أماكن جديدة لأغراض ترفيهية أو تعليمية.",
        exampleSentence = "السياحة في المغرب توفر تجربة ثقافية غنية."
    ),
    Word(
        word = "طبيعة",
        meaning = "العالم المادي من حولنا، بما في ذلك الأرض والماء والنباتات والحيوانات.",
        exampleSentence = "الطبيعة الخلابة في جبال لبنان تجذب الكثير من الزوار."
    ),Word(
        word = "عادات",
        meaning = "سلوكيات وتقاليد يتبعها الناس بشكل منتظم في حياتهم اليومية.",
        exampleSentence = "العادات والتقاليد تختلف من بلد عربي إلى آخر."
    ),
    Word(
        word = "تقاليد",
        meaning = "مجموعة من الأعراف والعادات التي تنتقل من جيل إلى جيل.",
        exampleSentence = "التقاليد العربية تشكل جزءًا مهمًا من هوية المجتمع."
    ),
    Word(
        word = "احتفال",
        meaning = "مناسبة اجتماعية يتم فيها التعبير عن الفرح أو التقدير لمناسبة خاصة.",
        exampleSentence = "الاحتفال بالعيد في البلدان العربية يختلف في بعض التفاصيل لكنه يشترك في روح الفرح."
    ),
    Word(
        word = "مسجد",
        meaning = "مكان العبادة للمسلمين.",
        exampleSentence = "المساجد في العالم العربي تتميز بالهندسة المعمارية الرائعة."
    ),
    Word(
        word = "لغة",
        meaning = "وسيلة للتواصل والتفاهم بين الناس.",
        exampleSentence = "اللغة العربية غنية بالمرادفات والمعاني العميقة."
    ),
    Word(
        word = "صحراء",
        meaning = "منطقة جافة وقاحلة تمتاز بقلة الأمطار والنباتات.",
        exampleSentence = "الصحراء الكبرى تمتد عبر عدة دول عربية."
    ),
    Word(
        word = "خيمة",
        meaning = "هيكل مصنوع من القماش يُستخدم كسكن مؤقت، وخاصة في الصحراء.",
        exampleSentence = "الخيمة تعتبر جزءًا من ثقافة البدو في شبه الجزيرة العربية."
    ),
    Word(
        word = "موسيقى",
        meaning = "فن تنظيم الأصوات بشكل يجذب الأذن ويعبر عن المشاعر.",
        exampleSentence = "الموسيقى الأندلسية هي جزء من التراث الموسيقي العربي."
    ),
    Word(
        word = "فنون",
        meaning = "الأشكال المختلفة للتعبير الإبداعي، مثل الرسم والنحت والمسرح.",
        exampleSentence = "الفنون العربية الإسلامية تتميز بالتفاصيل الهندسية والزخارف."
    ),
    Word(
        word = "زخرفة",
        meaning = "فن تزيين الأسطح بأنماط جميلة ومتكررة.",
        exampleSentence = "الزخرفة الإسلامية تستخدم الأشكال الهندسية بشكل مبدع."
    )



)

