@ if(status == 1) {
    <span class="label label-info arrowed-right arrowed-in">
        ${name}
    </span>
@ } else if(status == 2) {
    <span class="label label-warning arrowed arrowed-right">
        ${name}
    </span>
@ } else if(status == 3) {
    <span class="label label-danger arrowed arrowed-right">
        ${name}
    </span>
@ } else if(status == 5) {
    <span class="label arrowed arrowed-right">
        ${name}
    </span>
@ } else {
    <span class="label label-info arrowed arrowed-right">
        ${name}
    </span>
@ }