$(document).ready(function() {
    document.getElementById('pro-image').addEventListener('change', readImage, false);

    $( ".preview-images-zone" ).sortable();

    $(document).on('click', '.image-cancel', function() {
        let no = $(this).data('no');
        $(".preview-image.preview-show-"+no).remove();
    });

    var cancelImg = document.getElementsByClassName("image-cancel")
    if(cancelImg.length>0) num = parseInt(cancelImg[cancelImg.length-1].getAttribute("data-no")) + 1
});



var num = 0;
function readImage() {
    if (window.File && window.FileList && window.FileReader) {
        var files = event.target.files; //FileList object
        var output = $(".preview-images-zone");

        var cancelImg = document.getElementsByClassName("image-cancel")
        if(cancelImg.length+files.length>5){
            return
        }

        for (let i = 0; i < files.length; i++) {
            var file = files[i];
            if(file.size > 1 * 1024 * 1024){
                document.getElementById("erImg").innerText = "Kích thước tối đa của ảnh là 1mb"
                return;
            }
            if (!file.type.match('image')) continue;

            var picReader = new FileReader();

            picReader.addEventListener('load', function (event) {
                var picFile = event.target;
                var html =  '<div class="preview-image preview-show-' + num + '">' +
                    '<div class="image-cancel" data-no="' + num + '" id="'+files[i].lastModified+'" onclick="angular.element(this).scope().removeFile(this.id)">x</div>' +
                    '<div class="image-zone"><img id="pro-img-' + num + '" src="' + picFile.result + '"></div>' +
                    '</div>';

                output.append(html);
                num = num + 1;
            });

            picReader.readAsDataURL(file);
        }
        // $("#pro-image").val('');
    } else {
        console.log('Browser not support');
    }
}

