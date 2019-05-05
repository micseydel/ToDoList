// TODO: load might be later than necessary here
window.addEventListener("load", function() {
    createReminderButton();
    createReminderSection();
});

function createReminderButton() {
    const x = document.getElementById(":4");

    const block_to_insert = document.createElement('div');
    block_to_insert.innerHTML = "Create Reminder";

    const target = x.children[0].children[0].children[0];
    target.insertBefore(block_to_insert, target.children[2]);
}

function createReminderSection() {
    const sections = document.getElementById(":1").children[0].children[0].children[2];

    const new_section = document.createElement('div');
    new_section.setAttribute("id", "mseydel_test");
    new_section.setAttribute("class", "ae4 aDM");
    new_section.setAttribute("role", "tabpanel");
    new_section.appendChild(newSectionHeader());
    new_section.appendChild(newSectionList());

    sections.appendChild(new_section);
}

function newSectionHeader() {
    const span = document.createElement('span');
    span.setAttribute("class", "qh");
    span.innerHTML = "Reminders";

    const innermost_div = document.createElement('div');
    innermost_div.setAttribute("class", "Wn");
    innermost_div.appendChild(span);

    const h3 = document.createElement('h3');
    h3.setAttribute("class", "Wr");
    h3.appendChild(innermost_div);

    const inner_header = document.createElement('div');
    inner_header.setAttribute("class", "Cq");
    inner_header.appendChild(h3);

    const header = document.createElement('div');
    header.setAttribute("class", "nH Wg aAD aAr");
    header.appendChild(inner_header);

    const div = document.createElement('div');
    div.appendChild(header);

    return div;
}

function newSectionList() {
    const colgroup = document.createElement('colgroup');
    const classes = ["k0vOLb", "Ci", "y5", "WA", "yY", "null", "eSDBXb", "yg", "xX", "bq4"];
    for (let class_attr in classes) {
        const col = document.createElement('col');
        col.setAttribute("class", class_attr);
        colgroup.appendChild(col);
    }

    const table = document.createElement('table');
    table.setAttribute("cellpadding", "0");
    table.setAttribute("class", "F cf zt");
    table.appendChild(colgroup);
    table.appendChild(newReminderTableBody());

    const div = document.createElement('div');
    div.appendChild(table);

    const Cp = document.createElement('div');
    Cp.setAttribute("class", "Cp");
    Cp.appendChild(div);

    return Cp;
}

function newReminderTableBody() {
    const tr = document.createElement('tbody');
    tr.setAttribute("class", "zA yO btb");

    const td = document.createElement('td');
    td.setAttribute("class", "PF xY PE");
    // td.innerHTML = "::before";
    tr.appendChild(td);

    const box = (function () {
        const box = document.createElement('td');
        box.setAttribute("class", "oZ-x3 xY");
        box.setAttribute("style", 'cursor: url("//ssl.gstatic.com/ui/v1/icons/mail/images/2/openhand.cur") 7 5, default;');
        // box.innerHTML = "::before";
        tr.appendChild(box);

        const div = document.createElement('div');
        div.setAttribute("class", "oZ-jc T-Jo J-J5-Ji");
        div.setAttribute("role", "checkbox");

        const inner_div = document.createElement('div');
        inner_div.setAttribute("class", "T-Jo-auh");
        div.appendChild(inner_div);

        box.appendChild(div);

        return box;
    })();
    tr.appendChild(box);

    const star = (function () {
        const star = document.createElement('td');
        star.setAttribute("class", "apU xY");

        const span = document.createElement('span');
        span.setAttribute("class", "aXw T-KT");
        span.setAttribute("role", "button");

        const img = document.createElement('img');
        img.setAttribute("class", "T-KT-JX");
        img.setAttribute("src", "images/cleardot.gif");

        star.appendChild(span);

        return star;
    })();
    tr.appendChild(star);

    const important = (function () {
        const important = document.createElement('td');
        important.setAttribute("class", "WA xY");

        const important_div = document.createElement('div');
        important_div.setAttribute("class", "pG");
        important_div.setAttribute("role", "img");

        const important_inner_div0 = document.createElement('div');
        important_inner_div0.setAttribute("class", "T-ays-a45")

        const important_span = document.createElement('span');
        important_span.setAttribute("class", "aol");
        important_span.innerHTML = "[mseydel] Click to teach Gmail this conversation is important.";
        important_inner_div0.appendChild(important_span);

        const important_inner_div1 = document.createElement('div');
        // ignoring ::before and ::after
        important_inner_div1.setAttribute("class", "pH-A7 a9q");

        const important_inner_div2 = document.createElement('div');
        important_inner_div2.setAttribute("class", "bnj");

        important_div.appendChild(important_inner_div0);
        important_div.appendChild(important_inner_div1);
        important_div.appendChild(important_inner_div2);
        important.appendChild(important_div);

        return important;
    })();
    tr.appendChild(important);

    const sender = (function () {
        const sender = document.createElement('td');
        sender.setAttribute("class", "yX xY");

        const rando_div = document.createElement('div');
        rando_div.setAttribute("class", "afn");
        rando_div.innerHTML = "some placeholder text";
        sender.appendChild(rando_div);

        const sender_div = document.createElement('div');
        sender_div.setAttribute("class", "yW");

        const sender_span = document.createElement('span');
        sender_span.setAttribute("class", "bA4");

        const inner_span = document.createElement('span');
        inner_span.setAttribute("class", "yP");
        inner_span.setAttribute("email", "micseydel@gmail.com");
        inner_span.setAttribute("name", "micseydel");
        inner_span.innerHTML = "Michael Seydel"
        sender_span.appendChild(inner_span);

        sender_div.appendChild(sender_span);

        sender.appendChild(sender_div);

        return sender;
    })();
    tr.appendChild(sender);

    const description = (function () {
        const td = document.createElement('td');
        td.setAttribute("class", "xY a4W");
        td.setAttribute("tabindex", "-1");

        const div_xS = document.createElement('div');
        div_xS.setAttribute("class", "xS");
        div_xS.setAttribute("role", "link");

        const div_xT = document.createElement('div');
        div_xT.setAttribute("class", "xT");

        const div_y6 = document.createElement('div');
        div_y6.setAttribute("class", "y6");

        const span_bog = document.createElement('span');
        span_bog.setAttribute("class", "bog");

        const inner_span = document.createElement('span');
        inner_span.innerHTML = "Subject line"

        const span_y2 = document.createElement('span');
        span_y2.setAttribute("class", "y2");

        const span_Zt = document.createElement('span');
        span_Zt.setAttribute("class", "Zt");
        span_Zt.innerHTML = "&nbsp;-&nbsp;"

        span_y2.appendChild(span_Zt);
        span_y2.innerHTML = "Second subject line?"

        div_xT.appendChild(span_y2)

        span_bog.appendChild(inner_span);
        div_y6.appendChild(span_bog);
        div_xT.appendChild(div_y6);
        div_xS.appendChild(div_xT);
        td.appendChild(div_xS);

        return td;
    })();
    tr.appendChild(description);

    const td_byZ_xY = document.createElement('td');
    td_byZ_xY.setAttribute("class", "byZ xY");
    tr.appendChild(td_byZ_xY);

    const td_yf_xY = document.createElement('td');
    td_yf_xY.setAttribute("class", "yf xY");
    tr.appendChild(td_yf_xY);

    const date = (function () {
        const td = document.createElement('td');
        td.setAttribute("class", "xW xY");

        const span_title = document.createElement('span');
        span_title.setAttribute("title", "<full date>");

        const span_day = document.createElement('span');
        span_day.innerHTML = "May 3"

        return td;
    })();
    tr.appendChild(date);

    const end = (function () {
        const td = document.createElement('td');
        td.setAttribute("class", "bq4 xY");

        const ul = document.createElement('ul');
        ul.setAttribute("class", "bqY");
        ul.setAttribute("role", "toolbar");

        const li_archive = document.createElement('li');
        li_archive.setAttribute("class", "bqX brq");
        li_archive.setAttribute("data-tooltip", "Archive");
        li_archive.setAttribute("jsaction", "JqEhuc");
        li_archive.setAttribute("jscontroller", "pk1i4d");

        const li_delete = document.createElement('li');
        li_delete.setAttribute("class", "bqX bru");
        li_delete.setAttribute("data-tooltip", "Delete");
        li_delete.setAttribute("jsaction", "zM6fo");
        li_delete.setAttribute("jscontroller", "pmCKac");

        const li_mark_as_unread = document.createElement('li');
        li_mark_as_unread.setAttribute("class", "bqX brs");
        li_mark_as_unread.setAttribute("data-tooltip", "Mark as unread");
        li_mark_as_unread.setAttribute("jsaction", "XdlY1e");
        li_mark_as_unread.setAttribute("jscontroller", "VtSflc");

        const li_snooze = document.createElement('li');
        li_snooze.setAttribute("class", "bqX brv");
        li_snooze.setAttribute("data-tooltip", "Snooze");
        li_snooze.setAttribute("jsaction", "u4Fnue");
        li_snooze.setAttribute("jscontroller", "PKSrle");

        ul.appendChild(li_archive);
        ul.appendChild(li_delete);
        ul.appendChild(li_mark_as_unread);
        ul.appendChild(li_snooze);

        td.appendChild(ul);

        return td;
    })();
    tr.appendChild(end);

    const tbody = document.createElement('tbody');
    tbody.appendChild(tr);

    return tbody;
}
