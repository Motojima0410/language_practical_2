const addRowBtn = document.getElementById("add-row-btn");
const deleteRowBtn = document.getElementById("delete-row-btn");
const tableBody = document.getElementById("table-body");

//連番を付けなおす
function renumberRows() {
  const rows = tableBody.children;

  for (let i = 0; i < rows.length; i++) {
    rows[i].children[0].textContent = i + 1;
  }
}

//行を追加
addRowBtn.addEventListener("click", function () {
  const tr = document.createElement("tr");
  const tdNo = document.createElement("td");
  const tdCheck = document.createElement("td");
  const tdInput = document.createElement("td");
  const checkBox = document.createElement("input");
  const input = document.createElement("input");

  tdNo.textContent = tableBody.children.length + 1;

  checkBox.type = "checkbox";
  checkBox.classList.add("row-check");
  tdCheck.appendChild(checkBox);

  input.type = "text";
  input.classList.add("row-input");
  tdInput.appendChild(input);

  tr.appendChild(tdNo);
  tr.appendChild(tdCheck);
  tr.appendChild(tdInput);

  tableBody.appendChild(tr);
});

//行を削除
deleteRowBtn.addEventListener("click", function () {
  const rows = tableBody.querySelectorAll("tr");

  rows.forEach(function (row) {
    const checkBox = row.querySelector(".row-check");

    if (checkBox.checked) {
      tableBody.removeChild(row);
    }
  });

  renumberRows();
});