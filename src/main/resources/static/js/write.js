window.onload = function() {
bindDomEvent();
};

function bindDomEvent() {
var inputs = document.querySelectorAll(".custom-file-input");
inputs.forEach(function(input) {
  input.addEventListener("change", function() {
    var fileName = input.value.split("\\").pop();
    var fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
    fileExt = fileExt.toLowerCase();

//    var label = input.nextElementSibling;
//    label.innerHTML = fileName;

var label = input.parentNode.querySelector('.custom-file-label');
      var fileLabel = label.getAttribute("data-file-label");
      label.innerHTML = fileName ? fileName : fileLabel;
  });
});
}

