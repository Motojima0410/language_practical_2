const addRowBtn = document.getElementById("add-row-btn");
const tableBody = document.getElementById("table-body");

function renumberRows() {
  const rows = tableBody.children;
  for (let i = 0; i < rows.length; i++) {
    rows[i].children[0].textContent = i + 1;
  }
}

function attachDeleteEvent(deleteBtn, tr) {
  deleteBtn.addEventListener("click", function () {
    tableBody.removeChild(tr);
    renumberRows();
  });
}

const initialDeleteButtons = document.querySelectorAll(".delete-btn");
initialDeleteButtons.forEach(function (btn) {
  const tr = btn.closest("tr");
  attachDeleteEvent(btn, tr);
});

addRowBtn.addEventListener("click", function () {
  const tr = document.createElement("tr");
  const tdNo = document.createElement("td");
  const tdContent = document.createElement("td");
  const tdActions = document.createElement("td");

  tdNo.textContent = tableBody.children.length + 1;
  tdContent.textContent = "行" + (tableBody.children.length + 1);

  const deleteBtn = document.createElement("button");
  deleteBtn.textContent = "削除";
  deleteBtn.type = "button";
  deleteBtn.classList.add("delete-btn");
  attachDeleteEvent(deleteBtn, tr);

  tdActions.appendChild(deleteBtn);

  tr.appendChild(tdNo);
  tr.appendChild(tdContent);
  tr.appendChild(tdActions);

  tableBody.appendChild(tr);
});