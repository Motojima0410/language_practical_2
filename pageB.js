const nameButton = document.getElementById("button-name");
const outputName = document.getElementById("output-name");

nameButton.addEventListener("click", function () {
  const nameInput = document.getElementById("user-name").value;
  outputName.textContent = "名前：" + nameInput;
});

const inquiryButton = document.getElementById("button-inquiry");
const outputInquiry = document.getElementById("output-inquiry");

inquiryButton.addEventListener("click", function () {
  const inquiryInput = document.getElementById("inquiry-content").value;
  outputInquiry.textContent = "お問い合わせ内容：" + inquiryInput;
});

const genderButton = document.getElementById("button-gender");
const outputGender = document.getElementById("output-gender");

genderButton.addEventListener("click", function () {
  const genderInputs = document.getElementsByName("gender");
  outputGender.textContent = "性別：";
  for (let i = 0; i < genderInputs.length; i++) {
    if (genderInputs[i].checked) {
      outputGender.textContent = "性別：" + genderInputs[i].value;
    }
  }
});




const typeButton = document.getElementById("button-type");
const outputType = document.getElementById("output-type");

typeButton.addEventListener("click", function () {
  const typeInputs = document.getElementsByName("inquiry-type");
  let selectedTypes = [];

  for (let i = 0; i < typeInputs.length; i++) {
    if (typeInputs[i].checked) {
      selectedTypes.push(typeInputs[i].value);
    }
  }

  outputType.textContent = "お問い合わせ種別：" + selectedTypes.join(", ");
});


const addressButton = document.getElementById("button-address");
const outputAddress = document.getElementById("output-address");

addressButton.addEventListener("click", function () {
  const addressSelect = document.getElementById("address");
  const selectedAddress = addressSelect.value;
  outputAddress.textContent = "居住地：" + selectedAddress;
});