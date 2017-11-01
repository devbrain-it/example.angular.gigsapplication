export interface RoleJSON {
    // Diese Felder müssen im JSON Objekt der Antwort (Response) definiert sein!

    id: number;
    roleName: string;
    isDefault: boolean;
    description: string;
}
