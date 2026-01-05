function registerName() {
  const nameInput = document.getElementById("user-name");
  document.getElementById("output-name").textContent = "名前：" + nameInput.value;
}

function registerInquiry() {
  const inquiryInput = document.getElementById("inquiry-content");
  document.getElementById("output-inquiry").textContent = "お問い合わせ内容：" + inquiryInput.value;
}

function registerGender() {
  const genderInputs = document.getElementsByName("gender");
  let result = "性別：";
  for (let i = 0; i < genderInputs.length; i++) {
    if (genderInputs[i].checked) {
      result = "性別：" + genderInputs[i].value;
    }
  }
  document.getElementById("output-gender").textContent = result;
}

function registerType() {
  const typeInputs = document.getElementsByName("inquiry-type");
  let selectedTypes = [];
  for (let i = 0; i < typeInputs.length; i++) {
    if (typeInputs[i].checked) {
      selectedTypes.push(typeInputs[i].value);
    }
  }
  document.getElementById("output-type").textContent = "お問い合わせ種別：" + selectedTypes.join(", ");
}

function registerAddress() {
  const addressInput = document.getElementById("address");
  document.getElementById("output-address").textContent = "居住地：" + addressInput.value;
}


const registerButtons = document.getElementsByClassName("register-button");

for (const button of registerButtons) {
  button.addEventListener("click", function () {
    switch (this.id) {
      case "button-name":
        registerName();
        break;
      case "button-inquiry":
        registerInquiry();
        break;
      case "button-gender":
        registerGender();
        break;
      case "button-type":
        registerType();
        break;
      case "button-address":
        registerAddress();
        break;
    }
  });
}