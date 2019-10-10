let mainNextButton = document.querySelector(".bm-icon.spr-btn-arrow-main-slide.next");
const contents = document.querySelectorAll('.img-item');

let boxIndex = 0;

mainNextButton.addEventListener("click", function (e) {
    console.log(e);
    console.log("length: ", contents.length);
    console.log("index: ", boxIndex);

    boxIndex++;

    let el = document.querySelector(".img-box");

    if (boxIndex < contents.length) {
        el.style.transform = "translateX(-" + boxIndex * 100 + "%)";

    } else {
        console.log("over");
        el.style.transform = "translateX(0%)";
        boxIndex = 0;
    }

    document.querySelector(".dot-btn-box > .dot.on").className = "dot";
    document.querySelectorAll(".dot-btn-box > .dot").item(boxIndex).className = "dot on";
});

let mainPrevButton = document.querySelector(".bm-icon.spr-btn-arrow-main-slide.prev");

mainPrevButton.addEventListener("click", function (e) {
    console.log(e);
    console.log("length: ", contents.length);
    console.log("index: ", boxIndex);

    boxIndex--;

    let el = document.querySelector(".img-box");
    el.style.transform = "translateX(0%)";

    if (boxIndex >= 0) {
        el.style.transform = "translateX(-" + boxIndex * 100 + "%)";
    } else {
        console.log("under");
        el.style.transform = "translateX(-" + (contents.length - 1) * 100 + "%)";
        boxIndex = contents.length - 1;
    }

    document.querySelector(".dot-btn-box > .dot.on").className = "dot";
    document.querySelectorAll(".dot-btn-box > .dot").item(boxIndex).className = "dot on";
});


let sideNextButton = document.querySelector(".bm-icon.spr-btn-arrow-sliding-list.next");
let sideContents = document.querySelectorAll(".content-group > li");

let sideBoxIndex = 0;

sideNextButton.addEventListener("click", function () {
    console.log("index: ", sideBoxIndex);
    console.log("length: ", sideContents.length);

    if (sideBoxIndex < sideContents.length - 1) {
        sideContents.item(sideBoxIndex).className = "content-group prev";
        sideBoxIndex++;
        sideContents.item(sideBoxIndex).className = "content-group current";

        if (sideBoxIndex < sideContents.length) {
            sideContents.item(sideBoxIndex + 1).className = "content-group next";
        }
    }
});

let sidePrevButton = document.querySelector(".bm-icon.spr-btn-arrow-sliding-list.prev");

sidePrevButton.addEventListener("click", function () {
    console.log("index: ", sideBoxIndex);
    console.log("length: ", sideContents.length);

    if (sideBoxIndex > 0) {
        sideContents.item(sideBoxIndex).className = "content-group next";
        sideBoxIndex--;
        sideContents.item(sideBoxIndex).className = "content-group current";

        if (sideBoxIndex !== 0) {
            sideContents.item(sideBoxIndex - 1).className = "content-group prev";
        }
    }
});


let dots = document.querySelectorAll(".dot-btn-box > .dot");
let firstDot = dots.item(0);
let secondDot = dots.item(1);
let thirdDot = dots.item(2);
let fourthDot = dots.item(3);
let fifthDot = dots.item(4);
let el = document.querySelector(".img-box");

firstDot.addEventListener("click", function () {
    el.style.transform = "translateX(0%)";
    document.querySelector(".dot-btn-box > .dot.on").className = "dot";
    firstDot.className = "dot on";
    boxIndex = 0;
});

secondDot.addEventListener("click", function () {
    el.style.transform = "translateX(-100%)";
    document.querySelector(".dot-btn-box > .dot.on").className = "dot";
    secondDot.className = "dot on";
    boxIndex = 1;
});

thirdDot.addEventListener("click", function () {
    el.style.transform = "translateX(-200%)";
    document.querySelector(".dot-btn-box > .dot.on").className = "dot";
    thirdDot.className = "dot on";
    boxIndex = 2;
});

fourthDot.addEventListener("click", function () {
    el.style.transform = "translateX(-300%)";
    document.querySelector(".dot-btn-box > .dot.on").className = "dot";
    fourthDot.className = "dot on";
    boxIndex = 3;
});

fifthDot.addEventListener("click", function () {
    el.style.transform = "translateX(-400%)";
    document.querySelector(".dot-btn-box > .dot.on").className = "dot";
    fifthDot.className = "dot on";
    boxIndex = 4;
});


