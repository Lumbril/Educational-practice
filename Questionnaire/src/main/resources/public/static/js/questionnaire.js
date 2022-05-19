let questionNumber

function getQuestionList() {
    let url = "/question/list"

    fetch(url, {
        method: 'GET'
    })
        .then(res => res.json())
        .then(data => {
            questionNumber = data.questions.length
            console.log(data.questions)
            let i = 1
            for (const question of data.questions) {
                let questionTag = `<div class="question-text">${i}. ${question.questionText}</div>`
                let answers = []
                i++
                for (const answer of question.answerOptions) {
                    let answerTag =
                        `<div class="answer">
                        <label for="q${question.id}a${answer.id}">
                            <input type="radio" name="${question.id}" value="${answer.id}" id="q${question.id}a${answer.id}">
                            <div class="input-marker"></div>
                            <div>${answer.answerText}</div> 
                        </label>
                    </div>`
                    answers.push(answerTag)
                }


                let fullQuestionTag =
                    `<div class="question">
                        ${questionTag}
                        <div class="answers">
                            ${answers.join('')}
                        </div>
                    </div>`


                document.querySelector('.questions-wrapper').insertAdjacentHTML('beforeend', fullQuestionTag)
            }
        })
}

function collectAnswers(form) {
    let fd = new FormData(form)

    let url = '/question/answers'

    let answersArray = []

    for (const formDatum of fd) {
        let question = {
            questionId: formDatum[0],
            answerId: formDatum[1]
        }
        answersArray.push(question)
    }

    console.log(answersArray)

    if (answersArray.length < questionNumber) {
        alert('Ответьте на все вопросы, пожалуйста')
        return
    }

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            answers: answersArray
        })
    }).then(r => document.location.reload())

}

getQuestionList()