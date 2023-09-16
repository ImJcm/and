//중,하카테고리 출력

async function fetchMiddleCategories() {
    try {
        const responseMiddle = await fetch('/auth/middlecategory');
        const dataMiddle = await responseMiddle.json();

        if (dataMiddle && dataMiddle.middleCategoryList) {
            const middleCategoryListContainer = document.getElementById('middle-category-list');

            dataMiddle.middleCategoryList.forEach(category => {
                const categoryItem = document.createElement('div');
                categoryItem.classList.add('category-item');
                categoryItem.textContent = category.categoryName;
                categoryItem.addEventListener('click', () => {
                    // Load and display BottomCategory lists associated with this MiddleCategory
                    loadBottomCategories(category.id);
                });
                middleCategoryListContainer.appendChild(categoryItem);
            });
        }
    } catch (error) {
        console.error('Error fetching middle categories:', error);
    }
}

async function loadBottomCategories(middleCategoryId) {
    try {
        const response = await fetch(`/auth/middlecategory/${middleCategoryId}/bottomcategories`);
        const data = await response.json();
        const postingListsContainer = document.getElementById('posting-lists');
        postingListsContainer.innerHTML = ''; // Clear previous content

        if (data && data.bottomCategoryList) {
            const bottomCategoryListContainer = document.getElementById('bottom-category-list');

            data.bottomCategoryList.forEach(category => {
                const categoryItem = document.createElement('div');
                categoryItem.classList.add('category-item');
                categoryItem.textContent = category.categoryName;
                bottomCategoryListContainer.appendChild(categoryItem);
            });
        }
    } catch (error) {
        console.error('Error fetching bottom categories:', error);
    }
}

function showMiddleAndBottomCategories() {
    // Show the MiddleCategory and BottomCategory sidebar
    const sidebar = document.getElementById('middle-bottom-category-sidebar');
    sidebar.style.display = 'block';

    // Fetch and display MiddleCategory lists
    fetchMiddleCategories();
}