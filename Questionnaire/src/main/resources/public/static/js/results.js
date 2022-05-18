function getResults() {
    let url = '/question/count_right_answer'

    fetch(url)
        .then(res => res.json())
        .then(data => {
            document.querySelector('.result-number').innerHTML = data.count
        })
}

getResults()