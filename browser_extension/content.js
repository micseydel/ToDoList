// TODO: load might be later than necessary here
window.addEventListener("load", function() {
    createReminderButton()
    createReminderSection()
});

function createReminderButton() {
    var x = document.getElementById(":4")

    var block_to_insert = document.createElement('div');
    block_to_insert.innerHTML = "Create Reminder"

    var target = x.children[0].children[0].children[0]
    var inserted = target.insertBefore(block_to_insert, target.children[2])
}

function createReminderSection() {
    span = document.createElement('span');
    span.setAttribute("class", "qh")
    span.innerHTML = "Reminders"

    innermost_div = document.createElement('div');
    innermost_div.setAttribute("class", "Wn")
    innermost_div.appendChild(span)

    h3 = document.createElement('h3');
    h3.setAttribute("class", "Wr")
    h3.appendChild(innermost_div)

    inner_header = document.createElement('div');
    inner_header.setAttribute("class", "Cq")
    inner_header.appendChild(h3)

    header = document.createElement('div');
    header.setAttribute("class", "nH Wg aAD aAr")
    header.appendChild(inner_header)

    var new_section = document.createElement('div');
    new_section.appendChild(header)
    new_section.setAttribute("id", "mseydel_test");
    new_section.setAttribute("class", "ae4 aDM");
    new_section.setAttribute("role", "tabpanel");

    var sections = document.getElementById(":1").children[0].children[0].children[2]
    sections.appendChild(new_section)
}
