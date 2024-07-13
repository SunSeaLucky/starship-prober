import { useToast } from 'primevue/usetoast';
import { useConfirm } from 'primevue/useconfirm';
import { defineStore } from 'pinia';


export const useToastStore = defineStore('toast', () => {
    const toast = useToast();
    const confirm = useConfirm();

    const showSuccess = (msg) => {
        toast.add({ severity: 'success', summary: '成功', detail: msg, life: 3000 });
    };
    const showError = (msg) => {
        toast.add({ severity: 'error', summary: '错误', detail: msg, life: 3000 });
    };

    const showInfo = (msg) => {
        confirm.require({
            message: msg,
            header: '注入器提示',
            icon: 'pi pi-exclamation-triangle'
        });
    };

    return { showSuccess, showInfo, showError };
});
